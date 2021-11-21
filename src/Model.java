public class Model {
    final Log log = new Log();
    ClientServer actor;
    Boolean isServer = true;
    int typeApp;

    public Model(boolean isserv, int typeapp) {
        this.isServer = isserv;
        this.typeApp = typeapp;
    }

    public void start() {
        log.add(getClass().getSimpleName() + " startet at " + Utils.time());
        actor = new ClientServer(log, isServer, typeApp);
        new Thread(actor).start();
    }

    public void stop() {
        log.add(getClass().getSimpleName() + " stopped at " + Utils.time());
        actor.stop();
    }
}
