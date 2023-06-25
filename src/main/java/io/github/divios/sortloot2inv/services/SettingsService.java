package io.github.divios.sortloot2inv.services;

import com.github.divios.neptune_framework.core.annotations.Component;

@Component
public class SettingsService {

    private boolean status = true;

    public boolean getStatus() {
        return status;
    }

    public void toggleStatus() {
        status = !status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
