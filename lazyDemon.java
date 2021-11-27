package edyoda6;

  public  class lazyDemon {
        private static lazyDemon _instance;
        private static lazyDemon
                _instanceForDoubleCheckLocking;
        private boolean empty = false;
        private String patientName = "default";

        private lazyDemon()
        {
            System.out.println("Instance Created");
        }
        public static synchronized lazyDemon
        getInstanceSynchronizedWay()
        {

            if (_instance == null)
                _instance = new lazyDemon();

            return _instance;
        }
        public static lazyDemon
        getInstanceSynchronizedBlockWay()
        {

            // Checking for double locking
            if (_instanceForDoubleCheckLocking == null)
                synchronized (lazyDemon.class)
                {
                    if (_instanceForDoubleCheckLocking == null)
                        _instanceForDoubleCheckLocking
                                = new lazyDemon();
                }

            return _instanceForDoubleCheckLocking;
        }
        public boolean isOperationTheatreEmpty()
        {
            return empty;
        }
        public void endOperation() { empty = true; }

        public synchronized void operation(String aName)
        {

            if (empty == true) {
                patientName = aName;
                System.out.println("Operation can be done "
                        + "get ready patient "
                        + patientName);
                empty = false;
            }
            else {
                System.out.println(
                        "Sorry " + aName
                                + " Operation Theatre is busy with Surgery of "
                                + patientName);
            }
        }
    }
    class Hospital {


        public static void main(String args[])
        {
            Thread t1 = new Thread(new Runnable() {
                public void run()
                {
                    lazyDemon i1
                            = lazyDemon
                            .getInstanceSynchronizedWay();

                    System.out.println(
                            "The instance 1 in Synchronized Method is "
                                    + i1);
                    i1.endOperation();
                    i1.operation("123");
                }
            });
            Thread t2 = new Thread(new Runnable() {
                public void run()
                {

                    lazyDemon i2
                            = lazyDemon
                            .getInstanceSynchronizedWay();

                    System.out.println(
                            "The instance 2 in Synchronized Method is "
                                    + i2);
                    i2.operation("789");
                }
            });
            t1.start();

            t2.start();

            System.out.println(
                    "Double Checked locking - Synchronized Block only");
            Thread t3 = new Thread(new Runnable() {

                public void run()
                {

                    lazyDemon i1
                            = lazyDemon
                            .getInstanceSynchronizedBlockWay();

                    System.out.println(
                            "The instance 1 in Double Checked Locking way is "
                                    + i1);

                    i1.endOperation();
                    i1.operation("ABC");
                }
            });
            Thread t4 = new Thread(new Runnable() {

                public void run()
                {
                    lazyDemon i2
                            = lazyDemon
                            .getInstanceSynchronizedBlockWay();

                    System.out.println(
                            "The instance 2 in Double Checked Locking way is "
                                    + i2);

                    i2.operation("XYZ");
                }
            });

            t3.start();

            t4.start();
        }
    }

