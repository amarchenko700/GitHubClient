package com.example.githubclient.rxjava

import android.os.Handler
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class Sources {
    fun exec() {
        Consumer(Producer()).exec()
    }

    class Producer {
        private fun randomResultOperation(): Boolean {
            Thread.sleep(Random.nextLong(1000))
            return listOf(true, false, true)[Random.nextInt(2)]
        }

        fun completable() = Completable.create { emitter ->
            randomResultOperation().let {
                if (it) {
                    emitter.onComplete()
                } else {
                    emitter.onError(RuntimeException("Error"))
                    return@create
                }
            }
        }

        fun single() = Single.fromCallable {
            return@fromCallable "Some string value"
        }

        fun maybe() = Maybe.create<String> { emitter ->
            randomResultOperation().let {
                if (it) {
                    emitter.onSuccess("Success: $it")
                } else {
                    emitter.onComplete()
                    return@create
                }
            }
        }

        fun hotObservable() =
            Observable.interval(1, TimeUnit.SECONDS)
                .publish()

        fun hotObservable2() =
            Observable.interval(1, TimeUnit.SECONDS)
                .replay()

        fun hotObservableRefCount() =
            Observable.interval(1, TimeUnit.SECONDS)
                .publish()
                .refCount()

        fun hotObservableCache() =
            Observable.interval(1, TimeUnit.SECONDS)
                .cache()

        fun publishSubject() =
            PublishSubject.create<String>()

        fun create() = Observable.create<String> { emitter ->
            try {
                println(Thread.currentThread().name)
                for (i in 1..10) {
                    randomResultOperation().let {
                        if (it) {
                            emitter.onNext("Success$i")
                        } else {
                            emitter.onError(RuntimeException("Error"))
                            return@create
                        }
                    }
                }
                emitter.onComplete()
            } catch (t: Throwable) {
                emitter.onError(RuntimeException("Error"))
            }
        }
    }

    class Consumer(val producer: Producer) {
        fun execCompletable() {
            producer.completable()
                .subscribe({
                    println("onComplete Completable")
                }, {
                    println("onError Completable: ${it.message}")
                })
        }

        fun execSingle() {
            producer.single()
                .map { it + it }
                .subscribe({ s ->
                    println("onSuccess: $s")
                }, {
                    println("onError Completable: ${it.message}")
                })
        }

        fun execMaybe() {
            producer.maybe()
                .map { it + it }
                .subscribe({ s ->
                    println("onSuccess: $s")
                }, {
                    println("onError Completable: ${it.message}")
                }, {
                    println("onComplete")
                })
        }

        //HotObservable
        fun execHotObservable() {
//            val hotObservable = producer.hotObservable2()
            val hotObservable = producer.hotObservable2()//ColdObservable from HotObservable
            hotObservable.subscribe {
                println(it)
            }
            hotObservable.connect()

            Handler().postDelayed({
                hotObservable.subscribe {
                    println("delayed: $it")
                }
            }, 4000)
        }

        fun execRefCount() {
            val hotObservable = producer.hotObservableRefCount()//ColdObservable from HotObservable
            hotObservable.subscribe {
                println(it)
            }

            Handler().postDelayed({
                hotObservable.subscribe {
                    println("delayed: $it")
                }
            }, 4000)
        }

        fun execCache() {
            val hotObservable = producer.hotObservableCache()//ColdObservable from HotObservable
            hotObservable.subscribe {
                println(it)
            }

            Handler().postDelayed({
                hotObservable.subscribe {
                    println("delayed: $it")
                }
            }, 4000)
        }

        fun execPublishSubject() {
            val subject = producer.publishSubject()
            subject.subscribe({
                println("onNext $it")
            }, {
                println("onError ${it.message}")
            }
            )

            subject.onNext("from non reactive code")
        }

        fun execFromCreate() {
            producer.create()
//                .onErrorReturn {t->
//                    println(t.message)
//                    return@onErrorReturn "01"
//                }

                .onErrorResumeNext { t ->
                    return@onErrorResumeNext Observable.just("02")
                }

                .retry(5)

                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { s ->
                        println("onNext $s")
                        println(Thread.currentThread().name)
                    }, { e ->
                        println("onError ${e.message}")
                    }, {
                        println("onComplete")
                    }
                )

        }

        fun exec() {
            //execCompletable()
            //execSingle()
            //execMaybe()
            //execHotObservable()
            //execRefCount()
            //execCache()
            //execPublishSubject()
            execFromCreate()
        }

    }
}