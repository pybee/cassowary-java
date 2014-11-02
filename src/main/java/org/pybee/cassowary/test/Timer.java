package org.pybee.cassowary.test;

public class Timer
{
    public Timer()
    {
        timerIsRunning = false;
        elapsed = 0;
    }


    public void Start() {
        timerIsRunning = true;
        startReading = System.currentTimeMillis();
    }

    public void Stop()
    {
        timerIsRunning = false;
        elapsed += System.currentTimeMillis() - startReading;
    }

    //  Clears a Timer of previous elapsed times, so that a new event
    //  can be timed.
    public void Reset()
    {
        timerIsRunning = false;
        elapsed = 0;
    }

    // The data member, "timerIsRunning" is used to keep track of
    // whether a timer is active, i.e. whether an event is being
    // timed. While we want those using the timer class to know when a
    // timer is active, we do NOT want them to directly access the
    // timerIsRunning variable. We solve this problem, by making
    // timerIsRunning private and providing the public "access function"
    // below.
    public boolean IsRunning() {
        return timerIsRunning;
    }

    // This function allows a client to determine the amount of time that has
    // elapsed on a timer object. Note that there are two possibilities:

    // 1)  A timer object has been started and stopped. We can detect this
    //     case, because the variable "timerIsRunning" is false.

    // 2)  A timer object is "running", i.e. is still in the process of timing
    //     an event. It is not expected that this case will occur as frequently
    //     as case 1).

    // In either case, this function converts ticks to seconds. Note that
    // since the function TicksPerSecond() returns a value of type double,
    // an implicit type conversion takes place before doing the division
    // required in either case.

    public double ElapsedTime()
    {
        if (!timerIsRunning)
        {
            return (double) elapsed/1000;
        }
        else
        {
            return (double) (elapsed + System.currentTimeMillis() - startReading)/1000;
        }
    }

    private boolean timerIsRunning;
    private long elapsed;
    private long startReading;
}
