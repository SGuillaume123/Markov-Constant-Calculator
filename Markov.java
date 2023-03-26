// travail de Guillaume Sauvé et Cédric Raymond. Cédric: 0867477     Guillaume: 1440441
// nous n'avons pas trouvé de calculateur en ligne pour vérifier la validité de l'approximation

//source pour la résolution d'une erreur avec l'exposant et les division. (MathContext)
//https://stackoverflow.com/questions/9932239/java-bigdecimal-arithmaticexception-invalid-operation

//mon ryzen 2700x prend environ 6 secondes pour 5000 itérations. 
//l'affichage permet à l'utilisateur de comprendre que l'algorithme n'est pas figé et progresse
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;

public class Markov {


	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		boolean exit = false;
		
		do
		{
			int iteration = 0;
			
			boolean invalid_user_input = true;
			
			do
			{
				System.out.print("Entrer le nombre d'itérations: ");
				
				if(sc.hasNextInt())
				{
					iteration = sc.nextInt();
					//apres avoir laissé l'algorithme pendant plus de 1 heure pour 45 000 iteration, il n'a jamais planté
					if(iteration >= 1 && iteration <= 10000)
					{
						invalid_user_input = false;
					}
					else
					{
						System.out.println("Erreur, l'entré doit être entre 1 et 9999 inclusivement");
					}
				}
				else
				{
					System.out.println("Erreur, l'entré n'est pas un nombre entier");
				}
				sc.nextLine();
			}while(invalid_user_input);
			
			BigDecimal total = BigDecimal.ZERO;
			
			BigDecimal factorielle1 = BigDecimal.ONE;
			BigDecimal factorielle2 = BigDecimal.ONE;
			
			//constants
			BigDecimal two_point_five = new BigDecimal("2.5");
			BigDecimal negative_one = new BigDecimal("-1");
			BigDecimal two = new BigDecimal("2");
			
			for(Integer k = 1; k <= iteration; k++)
			{
				BigDecimal bigK = new BigDecimal(k.toString());
				BigDecimal doubleBigK = bigK.multiply(two);
				
				factorielle1 = factorielle1.multiply(bigK);
				
				factorielle2 = (factorielle2.multiply(doubleBigK.subtract(BigDecimal.ONE))).multiply(doubleBigK);
				
				BigDecimal numerateur = factorielle1.pow(2);
				BigDecimal denominateur = factorielle2.multiply(bigK.pow(3));
						
				// total += (2.5*-1^(k-1)) * ((k!)^2/((2k)!k^3))
				// total += (2.5*-1^(k-1)) * (numerateur/denominateur)
				total = total.add( //+=
						two_point_five.multiply( //(2.5*
								negative_one.pow(k-1, MathContext.DECIMAL128)).multiply(//-1^(k-1) *
										numerateur.divide(denominateur, MathContext.DECIMAL128)));//numerateur/denominateur
				
				System.out.println("iteration " + k);
			}
			
			System.out.println("Approximation de la constante d'Apéry par la méthode de Markov pour " + iteration + " itérations: \n" + total);
			
			System.out.print("1 pour continuer, autre pour quitter: ");

			if(!sc.nextLine().equals("1"))
			{
				System.out.println("Au revoir !");
				exit = true;
			}
		}while(!exit);
		
		sc.close();
		
	}
}
