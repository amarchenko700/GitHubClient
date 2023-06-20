package com.example.githubclient.rxjava

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function4
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.random.Random


private const val TAG = "Operators"

class Operators {
    fun exec() {
        Consumer(Producer()).exec()
    }

    class Producer {
        fun createJust() = Observable.just("1", "2", "3", "4", "4")
        fun createJust2() = Observable.just("5", "6", "7")
    }

    class Consumer(val producer: Producer) {
        private fun execTake() {
            Producer().createJust()
                .take(2)
                .subscribe(
                    { s -> println("onNext: $s") },
                    { e -> println("onError: ${e.message}") }
                )
        }

        private fun execSkip() {
            Producer().createJust()
                .skip(2)
                .subscribe(
                    { s -> println("onNext: $s") },
                    { e -> println("onError: ${e.message}") }
                )
        }

        private fun execMap() {
            Producer().createJust()
                .map { it + it }
                .subscribe(
                    { s -> println("onNext: $s") },
                    { e -> println("onError: ${e.message}") }
                )
        }

        private fun execDistinct() {
            Producer().createJust()
                .distinct()
                .subscribe(
                    { s -> println("onNext: $s") },
                    { e -> println("onError: ${e.message}") }
                )
        }

        private fun execFilter() {
            Producer().createJust()
                .filter() { it.toInt() > 1 }
                .subscribe(
                    { s -> println("onNext: $s") },
                    { e -> println("onError: ${e.message}") }
                )
        }

        private fun execMerge() {
            Producer().createJust()
                .mergeWith(producer.createJust2())
                .subscribe(
                    { s -> println("onNext: $s") },
                    { e -> println("onError: ${e.message}") }
                )
        }

        private fun execFlatMap() {
            Producer().createJust()
                .flatMap {
                    val delay = Random.nextInt(1000).toLong()
                    return@flatMap Observable.just(it + "x").delay(delay, TimeUnit.MILLISECONDS)
                }
                .subscribe(
                    { s -> println("onNext: $s") },
                    { e -> println("onError: ${e.message}") }
                )
        }

        fun execZip() {
            val observable1 = Observable.just("1", "7").delay(1, TimeUnit.SECONDS)
            val observable2 = Observable.just("2").delay(2, TimeUnit.SECONDS)
            val observable3 = Observable.just("3").delay(4, TimeUnit.SECONDS)
            val observable4 = Observable.just("4").delay(6, TimeUnit.SECONDS)

            Observable.zip(
                observable1,
                observable2,
                observable3,
                observable4,
                Function4<String, String, String, String, List<String>> { t1, t2, t3, t4 ->
                    return@Function4 listOf(t1, t2, t3, t4)
                }).subscribeOn(Schedulers.computation())
                .subscribe(
                    { s -> println("onNext: $s") },
                    { e -> println("onError: ${e.message}") }
                )
        }

        fun exec() {
            //execTake()
            //execSkip()
            //execMap()
            //execDistinct()
            //execFilter()
            //execMerge()
            //execFlatMap()
            execZip()
        }
    }
}