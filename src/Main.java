interface  IReport
{
    void  Generate();
}

class SalesReport implements IReport
{

    @Override
    public void Generate() {
        System.out.println("Sales Report");
    }
}

class  UserReport implements IReport
{

    @Override
    public void Generate() {
        System.out.println("User Report");
    }
}


abstract  class  ReportDecorator implements  IReport
{
    protected  IReport report;

    public ReportDecorator(IReport report)
    {
        this.report = report;
    }


    @Override
    public void Generate() {
        System.out.println("Report decorator");
    }
}



class  SourtongDecorator extends  ReportDecorator
{
    protected IReport report;

    public SourtongDecorator(IReport report)
    {
        super(report);
    }

    @Override
    public void Generate() {
        System.out.println("Generate Sourting" + super.report);

    }
}



class  DateFilterDecorator extends  ReportDecorator
{
    protected  IReport report;

    public  DateFilterDecorator(IReport report)
    {
        super(report);
    }

    @Override
    public void Generate() {
        System.out.println("Generate DateFilter" + " " + super.report);
    }

}

class CsvExportDecorator extends ReportDecorator
{

    protected IReport report;


    public CsvExportDecorator(IReport report){
        super(report);
    }

    @Override
    public void Generate() {
        System.out.println("Generate Scv" + " " + super.report);
    }
}


class  PdfExportDecorator extends ReportDecorator
{

    protected  IReport report;

    public PdfExportDecorator(IReport report)
    {
        super(report);
    }

    @Override
    public void Generate() {
        System.out.println("Generate pdf" + " " + super.report);
    }
}




public class Main {
    public static void main(String[] args) {
        IReport salereport = new SalesReport();
        IReport userreprt = new UserReport();
        IReport pdfreport = new PdfExportDecorator(salereport);
        pdfreport.Generate();


        IReport pdfUserReport = new PdfExportDecorator(userreprt);
        pdfUserReport.Generate();


        IReport csvreport = new CsvExportDecorator(salereport);
        csvreport.Generate();
    }
}



