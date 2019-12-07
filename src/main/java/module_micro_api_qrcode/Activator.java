package module_micro_api_qrcode;

import module_micro_api_qrcode.api.*;
import com.github.microprograms.osgi_module_activator.ModuleActivator;

public class Activator extends ModuleActivator {

    @Override
    protected void onStart() throws Exception {
        registerApis();
    }

    private void registerApis() {
        registerApi(new CreateQrcodeAsDataUrl());
    }
}
