package Function;

import Tools.GEV;
import Tools.OperasiMatriks;
import Integral.*;
import Posisi.*;
import Basis.*;
import JQC.Mainintegral;
import JQC.getdata;
import JQC.getpointvalue;

/**
 *
 * @author Agung Danu Wijaya
 */
public class Mainfunction {

    public SpecialFunction specialFunc = new SpecialFunction();
    public Fk b = new Fk(this);
    public OperasiMatriks matrixOp = new OperasiMatriks();
    public Posisi d = new Posisi();
    public F g = new F(this);
    public Rapatelektron rhoe = new Rapatelektron(this);                        //electron density
    public Kinetik T = new Kinetik(this);                                       //
    public Tarikanelektron nai = new Tarikanelektron(this);                     //nuclie attraction interaction
    public Tolakanelektron eri = new Tolakanelektron(this);                     //electron repultion interaction
    public Tarikanelektron_N naiN = new Tarikanelektron_N(this);                  //Numerical nuclie attraction interaction
    public Tolakanelektron_N eriN = new Tolakanelektron_N(this);                  //Numerical electron repultion interaction
    public GEV gev = new GEV();                                                 //General Eigen Value
    public Normalize cn = new Normalize(this);
    public Pangkat pkt = new Pangkat();
    public Geo geo = new Geo(this);
    public Data basisdata = new Data();
    public getdata gdata = new getdata(this);
    public Mainintegral intg = new Mainintegral(this);
    public getpointvalue gpoint = new getpointvalue(this);
}
