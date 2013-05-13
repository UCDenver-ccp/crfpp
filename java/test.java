import org.chasen.crfpp.Tagger;
import java.lang.UnsatisfiedLinkError;

public class test {

public static void main(String[] argv) {
  Tagger tagger = null;
  try {
  	tagger = new Tagger("-m ../model -v 3 -n2");
	}
	catch (UnsatisfiedLinkError e) {
		System.out.println("error: creating new Tagger: \n" + e);
		System.exit(-1);
	}
	catch (Exception x) {
		System.out.println("exception:" + x);
		System.exit(-1);
	}
	catch (Error x) {
		System.out.println("error:" + x);
		System.exit(-1);
	}

  // clear internal context
  tagger.clear();

  // add context
  tagger.add("Confidence NN");
  tagger.add("in IN");
  tagger.add("the DT");
  tagger.add("pound NN");
  tagger.add("is VBZ");
  tagger.add("widely RB");
  tagger.add("expected VBN");
  tagger.add("to TO");
  tagger.add("take VB");
  tagger.add("another DT");
  tagger.add("sharp JJ");
  tagger.add("dive NN");
  tagger.add("if IN");
  tagger.add("trade NN");
  tagger.add("figures NNS");
  tagger.add("for IN");
  tagger.add("September NNP");

  System.out.println("column size: " + tagger.xsize());
  System.out.println("token size: " + tagger.size());
  System.out.println("tag size: " + tagger.ysize());

  System.out.println("tagset information:");
  for (int i = 0; i < tagger.ysize(); ++i) {
    System.out.println("tag " + i + " " + tagger.yname(i));
  }

  // parse and change internal stated as 'parsed'
  if (!tagger.parse())
    return;

  System.out.println("conditional prob=" + tagger.prob()
                     + " log(Z)=" + tagger.Z());

  for (int i = 0; i < tagger.size(); ++i) {
    for (int j = 0; j < tagger.xsize(); ++j) {
        System.out.print(tagger.x(i, j) + "\t");
    }
    System.out.print(tagger.y2(i) + "\t");
    System.out.print("\n");

    System.out.print("Details");
    for (int j = 0; j < tagger.ysize(); ++j) {
      System.out.print("\t" + tagger.yname(j) + "/prob=" + tagger.prob(i,j)
                       + "/alpha=" + tagger.alpha(i, j)
                       + "/beta=" + tagger.beta(i, j));
    }
    System.out.print("\n");
  }

  // when -n20 is specified, you can access nbest outputs
  System.out.println("nbest outputs:");
  if (tagger == null) {
	System.out.println("error, null tagger ");
	System.exit(-1);
  }
  for (int n = 0; n < 10; ++n) {
	System.out.println("looping " + n);
    if (! tagger.next()) break;
    System.out.println("nbest n=" + n + "\tconditional prob=" + tagger.prob());
    // you can access any information using tagger.y()...
  }
  System.out.println("Done");
}

static {
  try {
    System.loadLibrary("CRFPP_JNI");
  } catch (UnsatisfiedLinkError e) {
    System.err.println("Test (static block): Cannot load the example native code.\nMake sure your LD_LIBRARY_PATH contains \'.\'\n" + e);
    System.exit(1);
  }
}

}
