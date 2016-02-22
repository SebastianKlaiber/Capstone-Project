package sklaiber.com.snow;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sklaiber.com.snow.models.Items;
import sklaiber.com.snow.models.Resort;
import sklaiber.com.snow.network.ResortService;
import sklaiber.com.snow.ui.main.MainContract;
import sklaiber.com.snow.ui.main.MainPresenterImpl;
import sklaiber.com.snow.ui.main.OnFinishedListener;

import static org.mockito.Mockito.verify;

public class MainPresenterTest {

  @Mock
  private MainContract.View mainView;

  @Mock
  private ResortService resortService;

  @Captor
  private ArgumentCaptor<OnFinishedListener> onFinishedListenerArgumentCaptor;

  private MainPresenterImpl mMainPresenter;

  @Before
  public void setUpMainPresenter() {
    MockitoAnnotations.initMocks(this);
    mMainPresenter = new MainPresenterImpl(mainView);
  }

  @Test
  public void loadResortsFromBackendAndLoadIntoView() {
    mMainPresenter.loadResorts();

    ArrayList<Resort> resorts = new ArrayList<>();
    resorts.add(new Resort());

    Items itemsStub = new Items();
    itemsStub.setItems(resorts);

    verify(resortService).getResort(onFinishedListenerArgumentCaptor.capture());
    onFinishedListenerArgumentCaptor.getValue().onFinished(itemsStub);

    verify(mainView).showResorts(itemsStub);
  }
}
