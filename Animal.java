/**
 * Represents an animal from the database
 *
 * nickname - name of the animal
 * animalID - ID of the animal in the database
 * feedingInfo - array of values about
 *
 */
public class Animal {
   public String nickname;
   public int animalID;
   public int[] feedingCleaningInfo = new int[3];
   public String chronoType;

   public Animal(int animalID, String animalNickName, String animalSpecies){

      this.animalID = animalID;
      this.nickname = animalNickName;

      String animalEnum = animalSpecies.toUpperCase();
      this.chronoType = AnimalSpecies.valueOf(animalEnum).getChronoType();
      this.feedingCleaningInfo = AnimalSpecies.valueOf(animalEnum).getFeedingCleaningInfo();

      //call mySqLConnection to get


   }
}
