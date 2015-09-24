package lc.buyplus.ble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lc.buyplus.R;
import lc.buyplus.cores.CoreActivity;
import lc.buyplus.customizes.MyCheckbox;
import lc.buyplus.customizes.MyTextView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ChooseDeviceActivity extends CoreActivity implements OnClickListener {
	private static final long serialVersionUID = 1L;
	private BluetoothAdapter mBluetoothAdapter;
    public static final String TAG = "DeviceListDialog";
    List<BluetoothDevice> deviceList;
    private DeviceAdapter deviceAdapter;
    Map<String, Integer> devRssiValues;
    private static final long SCAN_PERIOD = 3000; 
    private Handler mHandler;
    private boolean mScanning; 

    public MyTextView mBtCancel;
    public MyTextView mBtConnect;
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ble_choose_device);
        mHandler = new Handler();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            showToastLong(R.string.ble_not_support);
            finish();
        }
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
        	showToastLong(R.string.ble_not_support);
            finish();
            return;
        }
        populateList();
        initViews();
        initListener();
    }
    public void initViews() {
    	mBtCancel = (MyTextView) findViewById(R.id.device_list_dialog_cancel);
    	mBtConnect = (MyTextView) findViewById(R.id.device_list_dialog_connect);
    }
    public void initListener() {
    	mBtCancel.setOnClickListener(this);
    	mBtConnect.setOnClickListener(this);
    }
    private void populateList() {
        deviceList = new ArrayList<BluetoothDevice>();
        deviceAdapter = new DeviceAdapter(this, deviceList);
        devRssiValues = new HashMap<String, Integer>();

        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(deviceAdapter);
        newDevicesListView.setOnItemClickListener(new OnItemClickListener() {
            @SuppressWarnings({ "unused", "deprecation" })
    		@Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = deviceList.get(position);
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                Bundle b = new Bundle();
                b.putString(BluetoothDevice.EXTRA_DEVICE, deviceList.get(position).getAddress());
                setResult(Activity.RESULT_OK, new Intent().putExtras(b));
                finish();
            }
        });

        scanLeDevice(true);
    }
    
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
					mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                	addDevice(device, rssi);
                }
            });
        }
    };
    
    private void addDevice(BluetoothDevice device, int rssi) {
        boolean deviceFound = false;
        for (BluetoothDevice listDev : deviceList) {
            if (listDev.getAddress().equals(device.getAddress())) {
                deviceFound = true;
                break;
            }
        }
        devRssiValues.put(device.getAddress(), rssi);
        if (!deviceFound) {
        	deviceList.add(device);
            deviceAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void onStop() {
        super.onStop();
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }

    @SuppressWarnings("deprecation")
	@Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }

    
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
    }
    
    class DeviceAdapter extends BaseAdapter {
        public Context context;
        public List<BluetoothDevice> devices;
        public Boolean[] isCheckedList = new Boolean[1000];
        public LayoutInflater inflater;

        public DeviceAdapter(Context context, List<BluetoothDevice> devices) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.devices = devices;
        }

        @Override
        public int getCount() {
            return devices.size();
        }

        @Override
        public Object getItem(int position) {
            return devices.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewGroup vg;
            if (convertView != null) {
                vg = (ViewGroup) convertView;
            } else {
                vg = (ViewGroup) inflater.inflate(R.layout.ble_device_item, null);
            }

            BluetoothDevice device = devices.get(position);
            final MyCheckbox check = ((MyCheckbox) vg.findViewById(R.id.checkbox));
            final TextView tvadd = ((TextView) vg.findViewById(R.id.address));
            final TextView tvname = ((TextView) vg.findViewById(R.id.name));
            final TextView tvpaired = (TextView) vg.findViewById(R.id.paired);
            final TextView tvrssi = (TextView) vg.findViewById(R.id.rssi);
            
            
        	if(isCheckedList[position] != null) {
        		isCheckedList[position] = check.isChecked();
        	} else {
        		isCheckedList[position] = new Boolean(check.isChecked());
        	}
        	check.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(isCheckedList[position] != null) {
		        		isCheckedList[position] = check.isChecked();
		        	} else {
		        		isCheckedList[position] = new Boolean(check.isChecked());
		        	}
				}
			});
            
            tvrssi.setVisibility(View.VISIBLE);
            byte rssival = (byte) devRssiValues.get(device.getAddress()).intValue();
            if (rssival != 0) {
                tvrssi.setText("Rssi = " + String.valueOf(rssival));
            }

            tvname.setText(device.getName());
            tvadd.setText(device.getAddress());
            if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                Log.i(TAG, "device::" + device.getName());
                tvpaired.setVisibility(View.VISIBLE);
                tvpaired.setText(R.string.ble_paired);
                tvrssi.setVisibility(View.VISIBLE);
            } else {
                tvpaired.setVisibility(View.GONE);
                tvrssi.setVisibility(View.VISIBLE);
            }
            return vg;
        }
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.device_list_dialog_cancel:
			finish();
			break;
		case R.id.device_list_dialog_connect:
			int count = 0;
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<deviceList.size(); i++) {
				if(deviceAdapter.isCheckedList[i]) {
					count ++;
					sb.append(deviceList.get(i).getAddress());
					if(i != deviceList.size()-1) {
						sb.append("_");
					}
				}
			}
			if(count != 0) {
				showProgressDialog("Connecting to Rogo...");
				mBluetoothAdapter.stopLeScan(mLeScanCallback);
	            Bundle b = new Bundle();
	            b.putString(BluetoothDevice.EXTRA_DEVICE, sb.toString());
	            setResult(Activity.RESULT_OK, new Intent().putExtras(b));
	            finish();
	            removePreviousDialog();
			}
			break;
		default:
			break;
		}
		
	}
	@Override
	public void initModels() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initAnimations() {
		// TODO Auto-generated method stub
		
	}
}
