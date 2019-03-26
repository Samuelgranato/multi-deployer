/**
 * 
 */
package service;

import java.io.IOException;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.NullOutputReceiver;

import controller.Layout1Controller;

/**
 * @author user
 *
 */
public class LaunchService implements Runnable {

	private Layout1Controller controller;

	public LaunchService(Layout1Controller controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		controller.getBtnLaunch().setDisable(true);
		ProcessBuilder processBuilder = new ProcessBuilder();
		StringBuffer cmd = new StringBuffer();
		IDevice devices[] = controller.getAdb().getDevices();
		try {

			if (devices != null) {
				for (int i = 0; i < devices.length; i++) {
					devices[i].executeShellCommand("am start -n "
									+ controller.getHelper().getPackageName() + "/"
									+ controller.getHelper().getActivityName(), new NullOutputReceiver());
				}
			} else {
				controller.getTxaLog().appendText("No device is connected.");
			}
		} catch (IOException e) {
			controller.getTxaLog().appendText("IOException occured..\n");
			e.printStackTrace();
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			System.out.println(npe.getCause());
		} catch (Exception e) {
			System.out.println("Exception occured...\n");
			controller.getTxaLog().appendText("Exception occured..\n");
			e.printStackTrace();
		}
		controller.getBtnLaunch().setDisable(false);

	}

}