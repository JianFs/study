package com.tradwang.common.crash

import android.os.Handler
import android.os.Looper
import android.util.Log

/**
 *  @since 2018/1/29 14: 53
 *  @author : TradWang
 *  Package Name : com.tradwang.common.base
 *  @email : trad_wang@sina.com
 *  @version :
 *  @describe :
 */
object Cockroach {

    private var sExceptionHandler: ExceptionHandler? = null
    private var sUncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null
    /**
     * 标记位，避免重复安装卸载
     */
    private var sInstalled = false

    interface ExceptionHandler {
        fun handlerException(thread: Thread, throwable: Throwable)
    }

    private class DefaultExceptionHandler : ExceptionHandler {
        override fun handlerException(thread: Thread, throwable: Throwable) {
            try {
                Log.e("AndroidRuntime", "--->CockroachException:$thread<---", throwable)
            } catch (throwable: Throwable) {
            }
        }
    }

    /**
     * 当主线程或子线程抛出异常时会调用exceptionHandler.handlerException(Thread thread, Throwable throwable)
     *
     * exceptionHandler.handlerException可能运行在非UI线程中。
     *
     * 若设置了Thread.setDefaultUncaughtExceptionHandler则可能无法捕获子线程异常。
     *
     * @param exceptionHandler 异常
     */
    @Synchronized
    fun install(exceptionHandler: ExceptionHandler?) {
        if (sInstalled) {
            return
        }
        sInstalled = true
        sExceptionHandler = exceptionHandler

        Handler(Looper.getMainLooper()).post(Runnable {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    if (e is QuitCockroachException) {
                        return@Runnable
                    }
                    sExceptionHandler?.handlerException(Looper.getMainLooper().thread, e)
                }
            }
        })

        sUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            sExceptionHandler?.handlerException(t, e)
        }
    }

    /**
     * 当主线程或子线程抛出异常时会调用exceptionHandler.handlerException(Thread thread, Throwable throwable)
     *
     * exceptionHandler.handlerException可能运行在非UI线程中。
     *
     * 若设置了Thread.setDefaultUncaughtExceptionHandler则可能无法捕获子线程异常。
     */
    @Synchronized
    fun install() {
        if (sInstalled) {
            return
        }
        sInstalled = true
        sExceptionHandler = DefaultExceptionHandler()

        Handler(Looper.getMainLooper()).post(Runnable {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    if (e is QuitCockroachException) {
                        return@Runnable
                    }
                    sExceptionHandler?.handlerException(Looper.getMainLooper().thread, e)
                }
            }
        })

        sUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            sExceptionHandler?.handlerException(t, e)
        }
    }

    @Synchronized
    fun uninstall() {
        if (!sInstalled) {
            return
        }
        sInstalled = false
        sExceptionHandler = null
        //卸载后恢复默认的异常处理逻辑，否则主线程再次抛出异常后将导致ANR，并且无法捕获到异常位置
        Thread.setDefaultUncaughtExceptionHandler(sUncaughtExceptionHandler)
        Handler(Looper.getMainLooper()).post {
            //主线程抛出异常，迫使 while (true) {}结束
            throw QuitCockroachException("Quit Cockroach.....")
        }
    }
}
