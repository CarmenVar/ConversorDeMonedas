import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {

    private static final String[] TARGET_CURRENCIES = {"EUR", "CAD", "BRL", "ARS", "GBP"};
    private static final String[] TARGET_CURRENCY_NAMES = {"Euro", "Canadian Dollar", "Peso Brasileño", "Peso Argentino", "Pounds"};
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");

    public static double convert(String fromCurrency, String toCurrency, double amount) throws IOException, InterruptedException {
        double exchangeRate = ExchangeRateClient.getExchangeRate(fromCurrency, toCurrency);
        return amount * exchangeRate;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Map<String, String> currencies = ExchangeRateClient.getSupportedCurrencies();
            System.out.println("Monedas soportadas:");

            // Imprimir monedas en 4 columnas
            int columnCount = 0;
            for (Map.Entry<String, String> entry : currencies.entrySet()) {
                System.out.printf("%-15s : %-35s", entry.getKey(), entry.getValue());
                columnCount++;
                if (columnCount % 3 == 0) {
                    System.out.println();
                }
            }
            // Si el número de monedas no es múltiplo de 4, añade un salto de línea
            if (columnCount % 3 != 0) {
                System.out.println();
            }

            System.out.println("\n=============================\n");

            System.out.print("Ingrese la moneda base (código): ");
            String fromCurrency = scanner.nextLine().toUpperCase();

            System.out.print("Ingrese la cantidad a convertir: ");
            double amount = scanner.nextDouble();

            System.out.println("\n=============================\n");

            // Convertir la moneda base a USD
            double amountInUSD = convert(fromCurrency, "USD", amount);
            System.out.printf("%.2f %s equivale a %.2f USD%n", amount, fromCurrency, amountInUSD);

            System.out.println("\n=============================\n");

            // Convertir a las monedas de destino
            for (int i = 0; i < TARGET_CURRENCIES.length; i++) {
                double convertedAmount = convert(fromCurrency, TARGET_CURRENCIES[i], amount);
                System.out.printf("%.2f %s equivale a %.2f %s%n", amount, fromCurrency, convertedAmount, TARGET_CURRENCY_NAMES[i]);
            }

            System.out.println("\n=============================\n");

        } catch (IOException | InterruptedException e) {
            System.err.println("Ocurrió un error durante la conversión:");
            e.printStackTrace();
        }
    }
}



