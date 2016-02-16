package sklaiber.com.snow.network;

import sklaiber.com.snow.models.Items;
import sklaiber.com.snow.ui.main.OnFinishedListener;

public interface ResortService {
    Items getResort(OnFinishedListener listener);
}
