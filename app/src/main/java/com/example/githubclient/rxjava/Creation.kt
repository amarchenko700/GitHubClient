package com.example.githubclient.rxjava


import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit
import kotlin.random.Random


private const val TAG = "Creation"

class Creation {
    fun exec() {
        Consumer(Producer()).exec()
    }

    class Producer {
        private fun randomResultOperation(): Boolean {
            Thread.sleep(Random.nextLong(1000))
            return listOf(true, false, true)[Random.nextInt(2)]
        }

        fun create() = Observable.create<String> { emitter ->
            try {
                for (i in 0..10) {
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

        fun just(): Observable<String> {
            return Observable.just("1", "2", "3")
        }

        fun fromIterable(): Observable<String> {
            return Observable.fromIterable(listOf("1", "2", "3"))
        }

        fun interval() = Observable.interval(1, TimeUnit.SECONDS)

        fun timer() = Observable.timer(5, TimeUnit.SECONDS)

        fun range() = Observable.range(1, 10)

        fun fromCallable() = Observable.fromCallable {
            val result = randomResultOperation()
            return@fromCallable result
        }
    }

    class Consumer(private val producer: Producer) {
        var disposable: Disposable? = null

        private val stringObserver = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                disposable = d
                println("onSubscribe")
            }

            override fun onError(e: Throwable) {
                println("onError: ${e.message}")
            }

            override fun onComplete() {
                println("onComplete")
            }

            override fun onNext(s: String) {
                println("onNext: $s")
            }

        }

        private fun execFromIterable() {
            producer.fromIterable().subscribe(stringObserver)
        }

        private fun execJust() {
            producer.just().subscribe(stringObserver)
        }

        private fun execLambda() {
            val disposable = producer.just().subscribe(
                { s ->
                    println("onNext: $s")
                }, { e -> "onError: ${e.message}" },
                { println("onComplete") })
        }

        private fun execInterval() {
            producer.interval().subscribe {
                println("onNext: $it")
            }
        }

        private fun execTimer() {
            producer.timer().subscribe {
                println("onNext: $it")
            }
        }

        private fun execRange() {
            producer.range().subscribe {
                println("onNext: $it")
            }
        }

        private fun execFromCallable() {
            producer.fromCallable().subscribe {
                println("onNext: $it")
            }
        }

        private fun execCreate() {
            producer.create().subscribe(stringObserver)
        }

        fun exec() {
            //execJust()
            //execLambda()
            //execFromIterable()
            //execInterval()
            //execTimer()
            //execRange()
            execCreate()
        }
    }
}