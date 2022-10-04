package com.cursosdedesarrollo.app;


import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.observers.DisposableMaybeObserver;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        //Create the observable
        Single<String> testSingle = Single.just("Hello World");

        //Create an observer
        Disposable disposable = testSingle
                .delay(2, TimeUnit.SECONDS, Schedulers.io())
                .subscribeWith(
                        new DisposableSingleObserver<String>() {

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onSuccess(String value) {
                                System.out.println(value);
                            }
                        });
        Thread.sleep(3000);
        //start observing
        disposable.dispose();

        //Create an observer
        disposable = Maybe.just("Hello World")
                .delay(2, TimeUnit.SECONDS, Schedulers.io())
                .subscribeWith(new DisposableMaybeObserver<String>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onSuccess(String value) {
                        System.out.println(value);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Done!");
                    }
                });
        Thread.sleep(3000);
        //start observing
        disposable.dispose();

        //Create an observer
        disposable = Completable.complete()
                .delay(2, TimeUnit.SECONDS, Schedulers.io())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onStart() {
                        System.out.println("Started!");
                    }
                    @Override
                    public void onComplete() {
                        System.out.println("Done!");
                    }
                });
        Thread.sleep(3000);
        //start observing
        disposable.dispose();

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        //Create an Single observer
        Disposable disposableSingle = Single.just("Hello World")
                .delay(2, TimeUnit.SECONDS, Schedulers.io())
                .subscribeWith(
                        new DisposableSingleObserver<String>() {
                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onSuccess(String value) {
                                System.out.println(value);
                            }
                        });

        //Create an observer
        Disposable disposableMayBe = Maybe.just("Hi")
                .delay(2, TimeUnit.SECONDS, Schedulers.io())
                .subscribeWith(new DisposableMaybeObserver<String>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onSuccess(String value) {
                        System.out.println(value);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Done!");
                    }
                });

        Thread.sleep(3000);

        compositeDisposable.add(disposableSingle);
        compositeDisposable.add(disposableMayBe);

        //start observing
        compositeDisposable.dispose();



        final StringBuilder result1 = new StringBuilder();
        final StringBuilder result2 = new StringBuilder();

        PublishSubject<String> subject = PublishSubject.create();
        subject.subscribe(value -> result1.append(value) );
        subject.onNext("a");
        subject.onNext("b");
        subject.onNext("c");
        subject.subscribe(value -> result2.append(value));
        subject.onNext("d");
        subject.onComplete();

        //Output will be abcd
        System.out.println(result1);
        //Output will be d only
        //as subscribed after c item emitted.
        System.out.println(result2);


        final StringBuilder result3 = new StringBuilder();
        final StringBuilder result4 = new StringBuilder();
        BehaviorSubject<String> subject2 =  BehaviorSubject.create();
        subject2.subscribe(value -> result3.append(value) );
        subject2.onNext("a");
        subject2.onNext("b");
        subject2.onNext("c");
        subject2.subscribe(value -> result4.append(value));
        subject2.onNext("d");
        subject2.onComplete();
        //Output will be abcd
        System.out.println(result3);
        //Output will be cd being BehaviorSubject
        //(c is last item emitted before subscribe)
        System.out.println(result4);
    }


}
