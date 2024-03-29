package prog01;

/**
 * Class that represents a computer.
 */
public class Computer {
    // Data Fields
    private String manufacturer;
    private String processor;
    protected double ramSize;
    private int diskSize;
    protected double processorSpeed;

    public Computer(String man, String processor, double ram,
            int disk, double procSpeed) {
        manufacturer = man;
        this.processor = processor;
        ramSize = ram;
        diskSize = disk;
        processorSpeed = procSpeed;
    }

    public double getRamSize() {
        return ramSize;
    }

    public double getProcessorSpeed() {
        return processorSpeed;
    }

    public int getDiskSize() {
        return diskSize;
    }

    public String toString() {
        String result = "Manufacturer: " + manufacturer + "\nCPU: "
                + processor + "\nRAM: " + ramSize + " gigabytes"
                + "\nDisk: " + diskSize + " gigabytes"
                + "\nProcessor speed: " + processorSpeed + " gigahertz";

        return result;
    }


  /**
   * Evaluate the power of this computer for purposes of installing or running software.
   * @return the power.
   */
    public double computePower() {
        return ramSize * processorSpeed;
    }
}
