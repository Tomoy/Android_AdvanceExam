package madridshops.tomasm.com.repository.thread

import android.os.Handler
import android.os.Looper


fun DispatchOnMainThread(codeToRun: Runnable) {
    //Agarro la MainThread con el Handler y corro el código que me pasan en la main thread
    val uiHandler: Handler = Handler(Looper.getMainLooper())
    uiHandler.post(codeToRun)
}
