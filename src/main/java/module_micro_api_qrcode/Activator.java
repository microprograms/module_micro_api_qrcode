package module_micro_api_qrcode;

import com.github.microprograms.osgi_module_activator.ModuleActivator;

import module_micro_api_qrcode.api.CreateQrcodeAsDataUrl;

public class Activator extends ModuleActivator {

	@Override
	protected void onStart() throws Exception {
		registerApi(new CreateQrcodeAsDataUrl());
	}

}
