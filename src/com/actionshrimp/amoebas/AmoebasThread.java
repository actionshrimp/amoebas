package com.actionshrimp.amoebas;

class AmoebasThread implements Runnable {
	
	private AmoebasGame mGame;
	
	private boolean mPaused = false;
	private Object mPauseLock;
	
	private boolean mFinished = false;

	private long prevTime;
	
	public AmoebasThread(AmoebasGame amoebasGame) {
		mGame = amoebasGame;
		mPauseLock = new Object();
	}
	
	public void run() {
				
		prevTime = System.nanoTime();
		
		while(!mFinished) {
			long curTime = System.nanoTime();
			double dt = (curTime - prevTime)/1000000000.0f;
			prevTime = curTime;
			mGame.mManager.update(dt);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			synchronized(mPauseLock) {
				if (mPaused) {
					while (mPaused) {
						try {
							mPauseLock.wait();
						} catch (InterruptedException e) {
							//Pass
						}
					}
				}
			}		
		}
	}
	
	public void pauseGame() {
		synchronized(mPauseLock) {
			mPaused = true;
		}
	}
	
	public void resumeGame() {
		synchronized(mPauseLock) {
			mPaused = false;
			mPauseLock.notifyAll();
		}
	}
	
	public void stopGame() {
		synchronized (mPauseLock) {
			mPaused = false;
			mFinished = true;
			mPauseLock.notifyAll();
		}
	}
	
}