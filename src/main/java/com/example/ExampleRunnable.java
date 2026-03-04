package com.example;

public class ExampleRunnable implements Runnable {

    private Long userId;

    private static ScopedValue<Long> context = ScopedValue.newInstance();

    public ExampleRunnable(Long userId) {
        this.userId = userId;
    }

    @Override
    public void run() {

        ScopedValue.where(context, userId).run(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName()
                    + " User ID: " + context.get());
        });
        ;
    }
}
