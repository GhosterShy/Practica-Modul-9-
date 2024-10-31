interface IInternalDeliveryService {
    void deliverOrder(String orderId);
    String getDeliveryStatus(String orderId);
}

class InternalDeliveryService implements IInternalDeliveryService {
    @Override
    public void deliverOrder(String orderId) {
        System.out.println("Delivering order " + orderId + " via internal delivery service.");
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        return "Order " + orderId + " status: In Transit (Internal)";
    }
}


class ExternalLogisticsServiceA {
    public void shipItem(int itemId) {
        System.out.println("Shipping item " + itemId + " via External Logistics Service A.");
    }

    public String trackShipment(int shipmentId) {
        return "Shipment " + shipmentId + " status: In Transit (Service A)";
    }
}

class ExternalLogisticsServiceB {
    public void sendPackage(String packageInfo) {
        System.out.println("Sending package " + packageInfo + " via External Logistics Service B.");
    }

    public String checkPackageStatus(String trackingCode) {
        return "Package " + trackingCode + " status: Delivered (Service B)";
    }
}


class LogisticsAdapterA implements IInternalDeliveryService {
    private final ExternalLogisticsServiceA externalServiceA;

    public LogisticsAdapterA(ExternalLogisticsServiceA externalServiceA) {
        this.externalServiceA = externalServiceA;
    }

    @Override
    public void deliverOrder(String orderId) {
        int itemId = Integer.parseInt(orderId); // Преобразуем orderId в itemId
        externalServiceA.shipItem(itemId);
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        int shipmentId = Integer.parseInt(orderId); // Преобразуем orderId в shipmentId
        return externalServiceA.trackShipment(shipmentId);
    }
}

class LogisticsAdapterB implements IInternalDeliveryService {
    private final ExternalLogisticsServiceB externalServiceB;

    public LogisticsAdapterB(ExternalLogisticsServiceB externalServiceB) {
        this.externalServiceB = externalServiceB;
    }

    @Override
    public void deliverOrder(String orderId) {
        externalServiceB.sendPackage("Order#" + orderId);
    }

    @Override
    public String getDeliveryStatus(String orderId) {
        return externalServiceB.checkPackageStatus("Order#" + orderId);
    }
}





class DeliveryServiceFactory {
    public static IInternalDeliveryService getDeliveryService(String serviceType) {
        switch (serviceType) {
            case "Internal":
                return new InternalDeliveryService();
            case "ServiceA":
                return new LogisticsAdapterA(new ExternalLogisticsServiceA());
            case "ServiceB":
                return new LogisticsAdapterB(new ExternalLogisticsServiceB());
            default:
                throw new IllegalArgumentException("Unsupported delivery service type: " + serviceType);
        }
    }
}






public class Adapter {
    public static void main(String[] args) {

        IInternalDeliveryService internalService = DeliveryServiceFactory.getDeliveryService("Internal");
        internalService.deliverOrder("12345");
        System.out.println(internalService.getDeliveryStatus("12345"));


        IInternalDeliveryService serviceA = DeliveryServiceFactory.getDeliveryService("ServiceA");
        serviceA.deliverOrder("54321");
        System.out.println(serviceA.getDeliveryStatus("54321"));


        IInternalDeliveryService serviceB = DeliveryServiceFactory.getDeliveryService("ServiceB");
        serviceB.deliverOrder("98765");
        System.out.println(serviceB.getDeliveryStatus("98765"));


    }
}
