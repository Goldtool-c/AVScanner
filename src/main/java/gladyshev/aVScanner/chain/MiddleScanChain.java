package gladyshev.aVScanner.chain;

public abstract class MiddleScanChain {
    public MiddleScanChain next;
    public MiddleScanChain prev;
    protected String scanPath;
    public abstract void scan();
}
