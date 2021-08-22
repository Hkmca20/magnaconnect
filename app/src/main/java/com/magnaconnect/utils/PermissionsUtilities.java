/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 *
 * Copyright 2014 KC Ochibili
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 *  The "‚‗‚" character is not a comma, it is the SINGLE LOW-9 QUOTATION MARK unicode 201A
 *  and unicode 2017 that are used for separating the items in a list.
 */
package com.magnaconnect.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

public abstract class PermissionsUtilities {
	static final String TAG = PermissionsUtilities.class.getSimpleName();
	public static void goToSetting(FragmentActivity activity) {
		activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);

//		Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
//		dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		activity.startActivity(dialogIntent);
	}

	public static boolean getPermissionToReadAccounts(FragmentActivity activity) {
		if (Build.VERSION.SDK_INT < 23) {
		    return true;
		}
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            if (activity.shouldShowRequestPermissionRationale(Manifest.permission.GET_ACCOUNTS)) {
//            	Toast.makeText(activity, "Unable to login with google", 1).show();
            }
//            activity.requestPermissions(new String[]{Manifest.permission.GET_ACCOUNTS}, Host.ACCOUNTS_PERMISSIONS_REQUEST);
        	return false;
        } else {
        	return true;
        }
    }

	public static boolean getPermissionWriteDisk(FragmentActivity activity) {
		if (Build.VERSION.SDK_INT < 23) {
		    return true;
		}
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (activity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            	Log.e(TAG, "checking....permissions     denied-----##################----");
            }
//            activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Host.WRITE_STORAGE_PERMISSIONS_REQUEST);
        	return false;
        } else {
        	return true;
        }
    }
	public static boolean getPermissionReadContact(FragmentActivity activity) {
		if (Build.VERSION.SDK_INT < 23) {
		    return true;
		}
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            	Log.e(TAG, "checking....permissions     denied-----##################----");
            }
//            activity.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, Host.READ_CONTACTS_PERMISSIONS_REQUEST);
        	return false;
        } else {
        	return true;
        }
    }
	public static boolean getPermissionLocation(FragmentActivity activity) {
		if (Build.VERSION.SDK_INT < 23) {
		    return true;
		}
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            	Log.e(TAG, "checking....permissions     denied-----##################----");
            }
//            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Host.LOCATION_PERMISSIONS_REQUEST);
        	return false;
        } else {
        	return true;
        }
    }

	public static boolean insertContactWrapper(final Activity activity) {
		if (Build.VERSION.SDK_INT < 23) {
			return true;
		} else {
			int hasWriteContactsPermission = activity.checkSelfPermission(Manifest.permission.READ_CONTACTS);
			if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
				if (!activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
					showMessageOKCancel(activity, "You need to allow access to Contacts",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									if (Build.VERSION.SDK_INT == 23) {
//										activity.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, Host.READ_CONTACTS_PERMISSIONS_REQUEST);
									}
								}
							});
					return false;
				}
//				activity.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, Host.READ_CONTACTS_PERMISSIONS_REQUEST);
				return false;
			}
		}
		return true;
	}


	static void showMessageOKCancel(Activity activity, String message, DialogInterface.OnClickListener okListener) {
	    new AlertDialog.Builder(activity)
	            .setMessage(message)
	            .setPositiveButton("OK", okListener)
	            .setNegativeButton("Cancel", null)
	            .create()
	            .show();
	}

	public static boolean insertDummyContactWrapper(final Activity activity) {
		if (Build.VERSION.SDK_INT < 23) {
			Log.e(TAG, "Found....VERSION.SDK_INT < 23");
			return true;
		} else {
			List<String> permissionsNeeded = new ArrayList<String>();
			final List<String> permissionsList = new ArrayList<String>();

			if (!addPermission(activity, permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
				permissionsNeeded.add("Write Disk");
			if (!addPermission(activity, permissionsList, Manifest.permission.READ_CONTACTS))
				permissionsNeeded.add("Read Contacts");

			if (permissionsList.size() > 0) {
				if (permissionsNeeded.size() > 0) {
					// Need Rationale
					String message = "You need to grant access to ";// + permissionsNeeded.get(0);

					for (int i = 1; i < permissionsNeeded.size(); i++)
						message = message + ", " + permissionsNeeded.get(i);

					showMessageOKCancel(activity, message, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (Build.VERSION.SDK_INT == 23) {
//								activity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), Host.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
							}
						}
					});
					return false;
				}
//				activity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), Host.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
				return false;
			}
		}
	    return false;
	}
	
	static boolean addPermission(Activity activity, List<String> permissionsList, String permission) {
		if (Build.VERSION.SDK_INT < 23) {
			Log.e(TAG, "Found....VERSION.SDK_INT < 23");
			return true;
		} else {
			if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
				permissionsList.add(permission);
				// Check for Rationale Option
				if (!activity.shouldShowRequestPermissionRationale(permission)) {
					return false;
				}
			}
		}
	    return true;
	}
}