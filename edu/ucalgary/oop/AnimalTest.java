package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;

public class AnimalTest {

    @Test
    public void testFox() throws Exception{
        try{
            Animal fox = new Animal(6, "Annie, Oliver and Mowgli", "fox");

            String correctName = "Annie, Oliver and Mowgli";
            int correctID = 6;
            int[] correctInfo = new int[]{5,5,5};
            String correctChrono = "nocturnal";
            assertEquals("Fox does not have correct nickname",correctName, fox.NICKNAME);
            assertEquals("Fox does not have correct ID",correctID, fox.ANIMAL_ID);
            assertArrayEquals("Fox does not have correct info",correctInfo, fox.FEEDING_CLEANING_INFO);
            assertEquals("Fox does not have correct chrono type",correctChrono, fox.CHRONOTYPE);
        }catch(Exception e){
            fail("The fox object was not created correctly");
        }
    }
    
    @Test
    public void testCoyote() throws Exception{
        try{
            Animal coyote = new Animal(1, "Loner", "coyote");

            String correctName = "Loner";
            int correctID = 1;
            int[] correctInfo = new int[]{5,10,5};
            String correctChrono = "crepuscular";
            assertEquals("Coyote does not have correct nickname",correctName, coyote.NICKNAME);
            assertEquals("Coyote does not have correct ID",correctID, coyote.ANIMAL_ID);
            assertArrayEquals("Coyote does not have correct info",correctInfo, coyote.FEEDING_CLEANING_INFO);
            assertEquals("Coyote does not have correct chrono type",correctChrono, coyote.CHRONOTYPE);
        }catch(Exception e){
            fail("The coyote object was not created correctly");
        }
    }

    @Test
    public void testPorcupine() throws Exception{
        try{
            Animal porcupine = new Animal(8, "Spike", "porcupine");

            String correctName = "Spike";
            int correctID = 8;
            int[] correctInfo = new int[]{5,0,10};
            String correctChrono = "crepuscular";
            assertEquals("Porcupine does not have correct nickname",correctName, porcupine.NICKNAME);
            assertEquals("Porcupine does not have correct ID",correctID, porcupine.ANIMAL_ID);
            assertArrayEquals("Porcupine does not have correct info",correctInfo, porcupine.FEEDING_CLEANING_INFO);
            assertEquals("Pocupine does not have correct chrono type",correctChrono, porcupine.CHRONOTYPE);
        }catch(Exception e){
            fail("The porcupine object was not created correctly");
        }
    }

    @Test
    public void testBeaver() throws Exception{
        try{
            Animal beaver = new Animal(17, "Chomps", "beaver");

            String correctName = "Chomps";
            int correctID = 17;
            int[] correctInfo = new int[]{0,0,0};
            String correctChrono = "diurnal";
            assertEquals("Beaver does not have correct nickname",correctName, beaver.NICKNAME);
            assertEquals("Beaver does not have correct ID",correctID, beaver.ANIMAL_ID);
            assertArrayEquals("Beaver does not have correct info",correctInfo, beaver.FEEDING_CLEANING_INFO);
            assertEquals("Beaver does not have correct chrono type",correctChrono, beaver.CHRONOTYPE);
        }catch(Exception e){
            fail("The beaver object was not created correctly");
        }
    }

    @Test
    public void testRacoon() throws Exception{
        try{
            Animal racoon = new Animal(16, "Cooper", "racoon");

            String correctName = "Cooper";
            int correctID = 16;
            int[] correctInfo = new int[]{5,0,5};
            String correctChrono = "nocturnal";
            assertEquals("Racoon does not have correct nickname",correctName, racoon.NICKNAME);
            assertEquals("Racoon does not have correct ID",correctID, racoon.ANIMAL_ID);
            assertArrayEquals("Racoon does not have correct info",correctInfo, racoon.FEEDING_CLEANING_INFO);
            assertEquals("Racoon does not have correct chrono type",correctChrono, racoon.CHRONOTYPE);
        }catch(Exception e){
            fail("The racoon object was not created correctly");
        }
    }
}

