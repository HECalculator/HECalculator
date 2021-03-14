package liftingLug;/* Glavni kostur pograma

*  Treba da sadrzi primenu funkcija za unos, racunanje i prikaz.
*  Ovo je main funkcija koja po potrebi poziva ostale.
*
*  Funkcije za unos inputa za proracun
*
*   Potrebne funkcije za dobavljanje polaznih podataka od korisnika. Ovde spadaju svi polazni podaci kao sto su:
*       Geometrija lifting luga
*       Opterecenje na lugu
*       Materijal luga
*
*    Naming conventions
*    class name        should start with uppercase letter and be a noun e.g. String, Color, Button, System, Thread etc.
*    interface name    should start with uppercase letter and be an adjective e.g. Runnable, Remote, ActionListener etc.
*    method name       should start with lowercase letter and be a verb e.g. actionPerformed(), main(), print(), println() etc.
*    variable name     should start with lowercase letter e.g. firstName, orderNumber etc.
*    package name      should be in lowercase letter e.g. java, lang, sql, util etc.
*    constants name    should be in uppercase letter. e.g. RED, YELLOW, MAX_PRIORITY etc.
* */

public class HECalcProcedural {
    static double W;
    static String lugMat;
    static double YIELD;
    static double w;
    static double t;
    static double dh;
    static double r;
    static double h;
    static double off;
    static double fax;
    static double fn;
    static double ft;
    static double impfac;
    static double shs;
    static double sas;
    static double pbs;
    static double pba;
    static double fbs;
    static double fa;
    static double fcom;
    static double lall;

    static public void main(String[] args) {
        func_introduction();
        func_dummyInput(); // func_inputEcho();
        func_showInputs();
        func_calculate();
        func_report();
    }

    static void func_introduction() {
        System.out.println("");
        System.out.println("*****************************************************************************************");
        System.out.println("                                  LIFTING LUG ADEQUACY CALCULATOR");
        System.out.println("*****************************************************************************************");
        System.out.println("");
    }
    
    /**
     * Test function.
     * Placed here for test purposes. Remove in later builds.
     * 
     * @author Me
     */
    static void func_dummyInput() {
      W = 6500.0;
      lugMat = "SA/EN 10028-2";
      YIELD = 244.0;
      w = 120.0;
      t = 15.0;
      dh = 40.0;
      r = 40.0;
      h = 60.;        
      off = 80.0;
      fax = 165.0;
      fn = 0.0;
      ft = 3250.0;
      impfac = 2.0;
  }

    static void func_inputEcho() {
        System.out.println("Let's do inputs!\n");
        System.out.println("Input vessel weight W[N]. (This is for information only!)");
        System.out.print("\tW[N]:");
        W = TextIO.getlnDouble();
        System.out.println("Input lifting lug material. (This is for information only!)");
        System.out.print("\tLifting lug material:");
        lugMat = TextIO.getlnString();
        System.out.println("Input yield value for lifting lug material YIELD[MPa].");
        System.out.print("\tYIELD[MPa]:");
        YIELD = TextIO.getlnDouble();
        System.out.println("Input lifting lug maximum width w[mm]."); //proveriti kako definisati ovo? height/width
        System.out.print("\tw[mm]:");
        w = TextIO.getlnDouble();
        System.out.println("Input lifting lug uniform thickness t[mm].");
        System.out.print("\tt[mm]:");
        t = TextIO.getlnDouble();
        System.out.println("Input lifting lug hole diameter dh[mm]. Must be less than w!");
        System.out.print("\tdh[mm]:");
        dh = TextIO.getlnDouble();
        System.out.println("Input radius of semi-circular arc of lifting lug r[mm]. Less than w/2");
        System.out.print("\tr[mm]:");
        r = TextIO.getlnDouble();
        System.out.println("Input offset from vessel OD to center of hole h[mm].");
        System.out.print("\th[mm]:");
        h = TextIO.getlnDouble();        
        System.out.println("Input height of lifting lug from bottom to center of hole off[mm].");
        System.out.print("\toff[mm]:");
        off = TextIO.getlnDouble();
        System.out.println("Input force along vessel axis fax[N].");
        System.out.print("\tfax[N]:");
        fax = TextIO.getlnDouble();
        System.out.println("Input force normal to vessel fn[N].");
        System.out.print("\tfn[N]:");
        fn = TextIO.getlnDouble();
        System.out.println("Input force tangential to vessel ft[N].");
        System.out.print("\tft[N]:");
        ft = TextIO.getlnDouble();
        System.out.println("Input impact factor.");
        System.out.print("\tImpact factor:");
        impfac = TextIO.getlnDouble();
    }

    static void func_showInputs() {
        System.out.println("");
        System.out.println("*****************************************************************************************");
        System.out.println("                             SUMMARY OF INPUTS");
        System.out.println("");
        System.out.println("Vessel weight W:                                         " + W + "[N]");
        System.out.println("Lifting lug material:                                    " + lugMat);
        System.out.println("YIELD:                                                   " + YIELD + "[MPa]");
        System.out.println("Lifting lug maximum width w:                             " + w + "[mm]");
        System.out.println("Lifting lug uniform thickness t:                         " + t + "[mm]");
        System.out.println("Lifting lug hole diameter dh:                            " + dh + "[mm]");
        System.out.println("Radius of semi-circular arc of lifting lug r:            " + r + "[mm]");
        System.out.println("Height of lifting lug from bottom to center of hole h:   " + h + "[mm]");
        System.out.println("Offset from vessel OD to center of hole off:             " + off + "[mm]");
        System.out.println("Force along vessel axis Fax:                             " + fax + "[N]");
        System.out.println("Force normal to vessel Fn:                               " + fn + "[N]");
        System.out.println("Force tangential to vessel Ft:                           " + ft + "[N]");
        System.out.println("Impact factor:                                           " + impfac);
        System.out.println("*****************************************************************************************");
    }

    static void func_calculate() {
        fax = fax * impfac;
        ft = ft * impfac;
        fn = fn * impfac;
        func_calculateShearStress();
        func_calculateAllowableShearStress();
        func_calculatePinHoleStress();
        func_calculateAllowablePinHoleStress();
        func_calculateBendingStress();
        func_calculateTensileStress();
        func_calculateTotalCombinedStress();
        func_calculateLugAllowableStress();
    }

    static void func_calculateShearStress() {
        // Shear stress in lug above hole
        shs = Math.sqrt(Math.pow(fax, 2) + Math.pow(fn, 2) + Math.pow(ft, 2)) / (2 * (r - dh / 2)*t);
    }

    static void func_calculateAllowableShearStress() {
        // Allowable shear stress in lug above hole
        sas = 0.4 * YIELD;
    }

    static void func_calculatePinHoleStress() {
        // Pin hole bearing stress
        pbs = Math.sqrt(Math.pow(fax, 2) + Math.pow(fn, 2)) / (t * dh);
    }

    static void func_calculateAllowablePinHoleStress() {
        // Allowable bearing stress
        pba = 0.75 * YIELD;
    }

    static void func_calculateBendingStress() {
        // Bending stress at the base of the lug
        fbs = ft * off / (w * Math.pow(t, 2) / 6) + fax * off / (Math.pow(w, 2) * t / 6);
    }

    static void func_calculateTensileStress() {
        // Tensile stress at the base of the lug
        fa = fn / (w * t);
    }

    static void func_calculateTotalCombinedStress() {
        fcom = fbs + fa;
    }

    static void func_calculateLugAllowableStress() {
        // Allowable lug stress for bending and tension
        lall = 0.66 * YIELD;
    }

    static void func_report() {
        System.out.println("");
        System.out.println("*****************************************************************************************");
        System.out.println("                                  SUMMARY OF RESULTS");
        System.out.println("");
        System.out.println("          Stress                                   Actual\t\tAllowable\t  Utility [%]");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("Shear stress above hole [MPa]:                      %-5.2f\t\t  %-5.2f\t\t  %-5.2f \n", shs, sas, shs/sas*100);
        System.out.printf("Pin hole bearing stress [MPa]:                      %-5.2f\t\t  %-5.2f\t\t  %-5.2f \n", pbs, pba, pbs/pba*100);
        System.out.printf("Total combined stress at the lug base [MPa]:        %-5.2f\t\t  %-5.2f\t\t  %-5.2f \n", fcom, lall, fcom/lall*100);
        System.out.println("*****************************************************************************************");
    }
}