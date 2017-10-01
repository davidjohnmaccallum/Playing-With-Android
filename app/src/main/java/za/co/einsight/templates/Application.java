package za.co.einsight.templates;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialModule;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new MaterialModule());
        Iconify.with(new FontAwesomeModule());
    }

}
