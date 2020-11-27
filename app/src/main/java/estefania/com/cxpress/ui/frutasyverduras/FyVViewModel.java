package estefania.com.cxpress.ui.frutasyverduras;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FyVViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FyVViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}