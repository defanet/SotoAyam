package Function;

import Integral.*;
import LinearAlgebra.*;
import Posisi.*;
import Basis.*;
import JQC.MainIntegral;
import JQC.GetData;

/**
 *
 * @author Agung Danu Wijaya
 */
public class Mainfunction {

    public SpecialFunction specialFunc = new SpecialFunction();
    public fk b = new fk(this);
    public OperasiMatriks matrixOp = new OperasiMatriks();
    public Posisi d = new Posisi();
    public F g = new F(this);
    public Rapatelektron rhoe = new Rapatelektron(this);                        //electron density
    public Kinetik T = new Kinetik(this);                                       //
    public Tarikanelektron nai = new Tarikanelektron(this);                     //nuclie attraction interaction
    public Tolakanelektron eri = new Tolakanelektron(this);                     //electron repultion interaction
    public TarikanelektronN naiN = new TarikanelektronN(this);                  //Numerical nuclie attraction interaction
    public TolakanelektronN eriN = new TolakanelektronN(this);                  //Numerical electron repultion interaction
    public GEV gev = new GEV();                                                 //General Eigen Value
    public Normalize cn = new Normalize(this);
    public Pangkat pkt = new Pangkat();
    public Geo geo = new Geo(this);
    public Data basisdata = new Data();
    public GetData gdata = new GetData(this);
    public MainIntegral intg = new MainIntegral(this);
}
