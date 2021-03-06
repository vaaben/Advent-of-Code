package com.degofedal.advent.advent2017

/**
--- Day 7: Recursive Circus ---

Wandering further through the circuits of the computer, you come upon a tower of programs that have gotten themselves into a bit of trouble. A recursive algorithm has gotten out of hand, and now they're balanced precariously in a large tower.

One program at the bottom supports the entire tower. It's holding a large disc, and on the disc are balanced several more sub-towers. At the bottom of these sub-towers, standing on the bottom disc, are other programs, each holding their own disc, and so on. At the very tops of these sub-sub-sub-...-towers, many programs stand simply keeping the disc below them balanced but with no disc of their own.

You offer to help, but first you need to understand the structure of these towers. You ask each program to yell out their name, their weight, and (if they're holding a disc) the names of the programs immediately above them balancing on that disc. You write this information down (your puzzle input). Unfortunately, in their panic, they don't do this in an orderly fashion; by the time you're done, you're not sure which program gave which information.

For example, if your list is the following:

pbga (66)
xhth (57)
ebii (61)
havc (66)
ktlj (57)
fwft (72) -> ktlj, cntj, xhth
qoyq (66)
padx (45) -> pbga, havc, qoyq
tknk (41) -> ugml, padx, fwft
jptl (61)
ugml (68) -> gyxo, ebii, jptl
gyxo (61)
cntj (57)
...then you would be able to recreate the structure of the towers that looks like this:

                gyxo
              /
         ugml - ebii
       /      \
      |         jptl
      |
      |         pbga
     /        /
tknk --- padx - havc
     \        \
      |         qoyq
      |
      |         ktlj
       \      /
         fwft - cntj
              \
                xhth
In this example, tknk is at the bottom of the tower (the bottom program), and is holding up ugml, padx, and fwft. Those programs are, in turn, holding up other programs; in this example, none of those programs are holding up any other programs, and are all the tops of their own towers. (The actual tower balancing in front of you is much larger.)

Before you're ready to help them, you need to make sure your information is correct. What is the name of the bottom program?

  --- Part Two ---

The programs explain the situation: they can't get down. Rather, they could get down, if they weren't expending all of their energy trying to keep the tower balanced. Apparently, one program has the wrong weight, and until it's fixed, they're stuck here.

For any program holding a disc, each program standing on that disc forms a sub-tower. Each of those sub-towers are supposed to be the same weight, or the disc itself isn't balanced. The weight of a tower is the sum of the weights of the programs in that tower.

In the example above, this means that for ugml's disc to be balanced, gyxo, ebii, and jptl must all have the same weight, and they do: 61.

However, for tknk to be balanced, each of the programs standing on its disc and all programs above it must each match. This means that the following sums must all be the same:

ugml + (gyxo + ebii + jptl) = 68 + (61 + 61 + 61) = 251
padx + (pbga + havc + qoyq) = 45 + (66 + 66 + 66) = 243
fwft + (ktlj + cntj + xhth) = 72 + (57 + 57 + 57) = 243
As you can see, tknk's disc is unbalanced: ugml's stack is heavier than the other two. Even though the nodes above ugml are balanced, ugml itself is too heavy: it needs to be 8 units lighter for its stack to weigh 243 and keep the towers balanced. If this change were made, its weight would be 60.

Given that exactly one program is the wrong weight, what would its weight need to be to balance the entire tower?
 */
class Node(val name: String, val weight: Int, val children: List[String], val tree: List[Node]) {

  override def toString: String = {
    s"$name ($weight/$treeWeight) => $tree"
  }

  def done: Boolean = children.size == tree.size

  def treeWeight: Int = {
    weight + tree.map(_.treeWeight).sum
  }

  def ballanced: Boolean = {
    if(tree.isEmpty) {
      true
    } else {
      val l = tree.map(n => n.treeWeight)
      l.min == l.max
    }
  }

  def divergent: Option[(Node, Int)] = {
    if(tree.isEmpty) {
      None
    } else {
      val l = tree.map(n => n.treeWeight)
      val occurences = l.groupBy(identity).mapValues(_.size)
      if(occurences.size == 1) {
        // ballanced
        None
      } else {
        val m = occurences.find(o => o._2 == 1).get
        Some((tree.find(o => o.treeWeight == m._1).get,m._1))
      }
    }

  }

  def traverse: Unit = {
    val l = tree.map(n => n.treeWeight)
    val occurences = l.groupBy(identity).mapValues(_.size)
    if(occurences.size == 1) {
      println(this)
    } else {
      println(s"$name, $weight, $occurences")
      val m = occurences.find(o => o._2 == 1).get
      tree.find(o => o.treeWeight == m._1).get.traverse
    }
  }

}


object Node {

  def apply(str: String): Node = {
    val p = str.split("->")

    val nameWeight = p(0).split(" ")

    val name = nameWeight(0).trim
    val weight = nameWeight(1).substring(1,nameWeight(1).length-1).toInt

    if(p.size > 1) {
      val children = p(1).split(", ").toList.map(_.trim)
      new Node(name, weight, children, List())
    } else {
      new Node(name, weight, List(), List())
    }
  }
}

object Dec7 {

  val testInput = "pbga (66)\nxhth (57)\nebii (61)\nhavc (66)\nktlj (57)\nfwft (72) -> ktlj, cntj, xhth\nqoyq (66)\npadx (45) -> pbga, havc, qoyq\ntknk (41) -> ugml, padx, fwft\njptl (61)\nugml (68) -> gyxo, ebii, jptl\ngyxo (61)\ncntj (57)"
  val input = "occxa (60)\nkozpul (59) -> shavjjt, anujsv, tnzvo\ndotyah (138) -> tjhrdl, gdoxoc, aizbron\njgsvfsl (30)\neaxthh (5)\ndwklefi (82)\nrmlnt (199) -> epepfyw, dwrnlgg\nchrya (12)\ncvpimoj (164) -> eebyqij, vjipe, tdaxn\npfmegn (934) -> netob, zsrao, xaslwy\ntqbqd (79) -> fjterk, qaryj\nslgoac (81)\nzoxjtzq (67)\nqtwvlxq (81)\ncpnoezu (81)\nlwhywr (639) -> qhqqpj, qrtdqj, mjhuaca\nkuhvyy (59)\ncidfsbr (81)\nwwjtzuf (206)\nmpflt (90)\nysofby (54)\nttktong (63)\ncryim (43)\nlgacwzo (17)\nkuyofu (81)\nfgont (20)\nruqwlqw (99) -> ntnmzjx, nnxgmqu, eppyen\nlmbyxln (49)\nolynun (81) -> bpkcij, qutrc, itqaixc, jdbyghb\nzxjpf (35)\noooma (50)\njsanybb (39)\nbbycv (28) -> mevxgm, nxbeeb\ngfofc (38)\nivspii (14)\nvpodq (191) -> dcxivs, fdpan, rhprie\nsggzhh (7376) -> phshj, dryrgj, bzujyh, ktvdag, suqkyqc\nsakgzat (77) -> lklfzh, mpzhwng\noqhnzk (27) -> ekgzga, muzit, dnbpbn, sxyoft\nkkyby (181)\nhofab (98) -> zjdnsr, rxogya\nzwngnd (70)\nkqzwude (60)\njkltwg (57) -> nsssqpp, bxoyo, hcdxy, mqreu\nchrpwez (55)\ncmsfw (85) -> sjrdmho, cuice, rqtiz, obcbd\njtcxzs (31)\nwgquj (62) -> aotrzhg, acfeme\ncuice (44)\nptaorpg (225) -> tnxvkeq, yiihd\ngpwzru (736) -> jyztkjv, rmlnt, tltte, oqnbzpa\nnpmkva (30)\nzhhic (80)\nlrvcwu (73)\nuaerij (57) -> slauv, whckqd, mzrwlw, axfuq\nmguggym (31)\npjpcyya (60)\ngxbbwl (96) -> pzkfyje, dpily, dhwffod, kbdjrob, pwfoj, pwqlro\ncdhed (28)\neyttrr (34)\njqyrqjo (15)\noqzhbrd (34) -> dkktvs, fxzeacx, adrfscr\nscocac (62)\nftkcs (91)\njckhii (760) -> mbkcx, pgerst, qfhkrk, jdqxbag, qglqj\ntrotwoi (71)\nmcgncp (48)\nziwae (21)\nwriqytp (461)\ntnzvo (71)\ncyfkfo (67)\nzlrcjv (95)\ntqifa (99)\nzvkhqpa (30)\nnzdua (141)\nqjorpxg (242) -> dplsxrs, lbqwfgt\nyfjlrh (72) -> flwucgf, opdas\nxtaoa (97)\nmitlye (54)\nhnqjjhk (199) -> ruazjpw, cbhxz\nosaqp (85)\nisdxyo (68)\noexuzjy (177) -> zranvyg, hutmzof\nmtinmla (71)\nxvffp (90) -> bkoard, oizyf\naazgvmc (38513) -> dxoxxvk, zuahdoy, lopdq\neydxsk (76) -> epjrvvp, rakwfhk, bwhot, lrymy\nqhosh (40) -> efozgh, ulxmu, ixtywxj, neagw\nbthky (132) -> liedl, ghicb\nkiuysw (62)\nneawpkj (207) -> evetqs, twemenc\nncerbp (29)\nwoioqeb (347)\nhbnsqgy (69) -> achjhb, ftqnpo\ntltte (127) -> ojhkvb, lgpewn\nqdlqy (57)\niittzki (43)\noqnbzpa (57) -> fxmyl, rptcitf\nmdhmr (71)\nkqnkzdl (76)\ndcmndzz (83)\nvkxicq (79)\nzvysgi (28)\nfxzeacx (87)\nnexgmr (24)\nwkxnnrg (90)\nrwvns (101) -> lgacwzo, zcslj, wopanki\nszphud (84) -> unbfqv, dvotce, rjgndzn\ndquww (98)\npzkfyje (399)\niwwbqle (16)\njupsbj (32)\ndyibh (73)\nngicn (30)\nsmswtc (31) -> miyzuba, extlmn, kextbaa, zidgf\nqmghpah (358) -> vtdovx, acuce\njyztkjv (155) -> iqccq, mcgncp\nlrcfe (96)\ntnxvkeq (24)\nnoymo (11)\nwvmfzh (91)\nwjnhlj (132) -> whehan, lktfcet\nwzlfky (34)\nzpmslbn (291) -> ivspii, rbadc, wofzjph, obrfxlp\neylle (9)\niattz (29)\nruzrr (67)\nbilrn (67)\nouszgvx (82)\nxamoxye (11)\nwsbkos (75) -> hvaqke, fwtbaig\nxphjp (66)\npxdnb (11729) -> jdmmnpg, xtcdf, qdcbmq, iztqqg, xluprc, kumzgqd\ntrnzjj (219) -> wriqytp, amcrzt, fjhroj\nrxurj (5)\nboydbo (82)\nptrqmgy (74)\nioobi (197)\nslauv (65)\nksqwp (142) -> kjjzo, qaxlov\nqnfxh (125) -> ihqhmp, kqnkzdl\nklpjjvg (44)\nwbqmyt (304)\nqfkkwc (428) -> qlkwu, xxgbjxv, zyzjfr\nqaryj (52)\ncvyof (32)\ntmeconw (77)\neavzu (53)\njmfvn (21)\ndvxkr (58)\nlsckdh (39) -> wdmqsw, mmwijic, dhsqdfu, sovbjm\nuownj (12) -> wqdviv, ctmydr, pxdnb, qipooo, aazgvmc, eidmwnu\nkbdjrob (237) -> uwlec, mranee\nxuuboqp (39)\nggapah (22) -> occxa, fcila, kqzwude\ndmpmj (41)\nicpczr (16)\naztlqe (70)\nrvjfiqd (61)\nxslcw (61) -> fleler, mslwcef, qybci\ntxuqarh (1083) -> jgxxaah, bjdps, vyjyqm\ngmlyf (76)\nwqdviv (22436) -> wwfsfm, gxxrr, sggzhh\nxxkbo (156) -> dkpebdn, hepoax, qgaal, jcpaww\nmshtzph (85)\ncbprshs (10) -> lpjyqfd, gimjvji\nhcdxy (68)\nbrekp (70)\nbjdps (260) -> kyfjsn, dkdttm\nycsha (30)\nbxvht (65)\nhicwq (98)\nzrdvbl (71)\nagvef (57)\nhvaqke (25)\nquayoh (57)\nnbppbiy (48)\nmleek (90) -> lwisav, iatxfon\naxfuq (65)\ngtzfc (73)\ntxelahj (81)\niclgmrd (58)\npfkkpf (69)\nkiiwvl (63)\nupnvuf (98) -> gtosn, mpflt, ahdjfhr\nwsmdf (66)\navfse (71)\ncfnxaxh (176) -> zpilz, bfqgrx\nkvxtoa (80)\ngsqag (55)\nnnrme (32)\nsovbjm (77)\nihqhmp (76)\nxptngy (76) -> xgfnh, pedyx\nqdufhxj (120)\njvvrtrv (54)\nldjcv (31)\nhiunh (162) -> fnnaq, ihesljo\nywlwppw (107) -> vkmmhj, rtrqyt, jnmhgn\nnnwvn (81)\nzpilz (24)\npuqymd (183)\ncxndnyb (73)\nnaxvkt (178) -> mguggym, ccodif\neprwoep (93)\nmjatmj (83) -> wwumy, rvvrxj, aroct\npsnhw (81)\ncwxpvg (93)\nkdcbtyf (317)\nattvd (238) -> iznrffp, znksd\nqtlyvzn (32)\nztowevm (75)\nnxbeeb (84)\njdmmnpg (5564) -> dagpv, wkvfo, jrphokq\ncocpc (53)\nseehj (74)\nkphqbqy (872) -> cqjena, mwtcdof\nlwmlmt (85)\nmmwijic (77)\nrtrbpze (337) -> xlhtsn, hyvee, cyxpp\nihesljo (78)\nrbadc (14)\notrnol (98)\nahjzy (134) -> faysq, zirepil\nrvzbog (53) -> dcmndzz, viojqk\nzzqucm (96) -> thnxcx, lelkjof, cpnoezu, qtwvlxq\nxphdneu (39)\nrcuauq (78)\nzyzjfr (226)\nlnezc (80)\nfnsol (28)\niljnbiz (228) -> ziwae, tuylou, meecdj\nqlkwu (86) -> aztlqe, knmyjju\nuyxck (73)\nmfgah (83)\niznrffp (40)\nohxkp (87)\nsoypqq (80)\njnmhgn (83)\nvbjrc (241) -> iattz, ncerbp, yzbem\nobsfib (272)\nxgfnh (65)\nhjddl (13)\ngvtpttw (72)\nxbuelds (9)\nfmtveft (104) -> mmmqt, wkxnnrg\ntxexr (28)\nkjtclbz (68) -> ruzrr, bqqbtuu\ndtnlyc (98)\nflmcu (66)\ngbikgsz (250)\njdsfjkf (52) -> kyqidwo, ysyjko, tihmp, urcmmqc, grvssv\nfbtok (42)\natngvo (344) -> dlhnqsu, ayrzzu\ndwfoa (87)\nachjhb (23)\nswgnd (96)\npiedp (337) -> eydxsk, wbqmyt, xmdpfk\nvzmxrqs (256) -> fduhgla, bysqw\nlxeit (84) -> npejje, yhkfv, fbtok, eksvho\nfsznv (96)\nuvekre (93)\neykvd (213) -> scgjs, pusfms\naizbron (27)\nhekaxal (251) -> xamfydv, nnrme, qzhlkec\nlbjvtj (19) -> isdxyo, ahhobtu, vkvdnam, fasqn\nqtaka (298) -> trwmicb, kdcbtyf, uaerij\nwdcers (12)\npsftlm (110) -> zoxjtzq, gawxacm\nhbxquoz (72)\niyivq (174) -> ztzerr, nalot\nozynj (11)\nqngdf (93)\nrtpusqx (1731) -> awywkmu, cbprshs, jpfcy\nvfmuj (95)\nqutrc (292)\nxcxroor (16)\ncxcibc (12)\nhcfghl (31) -> rnoivcb, swgnd, fsznv\npnbrg (8558) -> pfmegn, mevgyhq, bhgzfhp\nyiaxyi (29)\nlktfcet (10)\npsesrd (18)\nhutmzof (50)\nddzvqkr (1020) -> asyku, yfjlrh, naxvkt\nrbbtkcf (21)\nctmydr (94) -> eiqfjh, swiyje, pnbrg, vseri, dbcevsv\ntkzjfkq (47)\ngvrmz (60)\nktvnccr (53)\nnglegc (105) -> sdamh, tmonbh\nackza (6)\nqipooo (46244) -> udaqlo, eltxjp, locrn\ntrczg (140) -> eylle, qbxat\nmbljzmm (370) -> sorgx, bxakn, itokb\noycjqok (79) -> nmdgz, pyozvr, bljxv\nobcbd (44)\nunhzsf (46) -> jkltwg, twbmbv, cedcjza, xywrttu\ncdubxqs (73)\nsoweon (308) -> iwwbqle, xcxroor, icpczr\njyavyin (455) -> nwzrdt, gbikgsz, hhoafz, iyivq\nxfqbdst (98) -> npzfhkv, hcfghl, neawpkj, uiecu, cijsvr, wucysx, dsgkaw\nysphvx (12)\npukgrz (121) -> opcszf, hvvlhe\ndvotce (253) -> kiiwvl, ttktong\nnxphajd (78) -> ynoxdm, bqvid\ntwbmbv (45) -> kuolv, yvhgp, clmdi, sbwfpa\nyhkxud (140) -> xlajoju, yiaxyi\neckrps (63)\nxtnctiv (275) -> hnqjjhk, lqsjdu, mfvzrxh\nwofzjph (14)\nnvbav (31)\nxamfydv (32)\nwxpae (180) -> vlmtckh, drxfuqd, viaivun\nxiiwbs (669) -> tptuf, loyiwus, pfluh, bchbcjc, qjorpxg, guxwpqu\nslbbde (94) -> agexcnl, wxscr\npubnb (98)\nqayhfqp (81)\napjgsl (232) -> bpuuio, qpqwnti\nucodfmw (18)\nfmwgpf (69)\nsbwfpa (71)\nmvwvgtc (60)\nyykvyt (96)\ndycdyx (76)\nitnlnpw (87)\nklgfmp (121) -> spyey, qrzjxx, vtkkzt\nbchbcjc (205) -> emndhg, otkntx, tvhttj\nrtrqyt (83)\nbdrgk (1340) -> jwpff, mvwvgtc\nqadmk (18)\nefqeq (30)\npyhrwp (61)\njdqxbag (45) -> wzcrtz, endts, aajml, bprczj\nwngzj (98)\nggrzmj (66)\nlbqwfgt (37)\nvahqnf (79)\npktrea (114) -> fmrcz, cdubxqs, dlkhgy, enumule\nhyhkb (89) -> trotwoi, mtwod\ndodxloj (57)\ngizfx (79)\ntyvrby (49) -> kgifnwx, wvmfzh\ncomkkpi (79)\nozkxsak (74)\ndaznpq (29)\nysvmli (91)\nwonidli (41) -> qogcxh, mkthfc, eemtac, kuhvyy\nwbpqtn (121) -> nvbav, yqhlag\nvhlyct (31)\nbcknuwr (145) -> zsgntfb, tyueun\nqbqdcm (60)\nvseri (5387) -> mekxoeo, aavyxnd, qtaka, piedp, uegzzq, olynun\nsorgx (32) -> qwmqr, jgsvfsl, npmkva\ngeexh (867) -> apyvj, idmwnxg, hgirpmg, yhkxud\ncfwjs (57)\nxaslwy (133) -> sfqps, ossumc\nazowpjp (2022) -> kkyby, sakgzat, avsqfag\nurarg (53)\ndxzrqa (28)\nobrfxlp (14)\nbxiroo (17)\nxkwuq (78)\nmsnnf (265) -> bxvht, jtnoqx\nuiecu (319)\natfeuv (60) -> snwwnem, ycsha\ngimjvji (95)\nbuhyicq (11)\nylavfs (154) -> nbarshr, osaqp\nyjvso (18)\nzmkno (72)\nnvold (2040) -> jssuppj, bfmwj\nruazjpw (39)\nmekxoeo (1133) -> iclgmrd, dvxkr\nxspgdc (56)\nyjhyb (73)\nyptmfb (259) -> qtsfwnv, gksdobg, fuxir\ndxoxxvk (643) -> ozafzy, flsqqw, yfxlqu\npeiiks (11)\ngcqeyqn (84)\nygqakoh (60)\nrjgndzn (201) -> ncxfef, hlkerz\nynnefdj (88)\ntbsrvbd (72)\nawywkmu (60) -> veojykb, brekp\nnpyxpyc (48)\ntndlco (2699) -> ddzvqkr, gpwzru, oqmliz, oxada\nvpapi (28)\nhlkerz (89)\npzmvwhg (77)\njtmbf (93)\nmcwhhll (92)\nmevxgm (84)\nagexcnl (41)\nguxwpqu (199) -> jsanybb, wlxgck, jtzidh\noyjliid (63)\noqmliz (32) -> fxzmev, axhgfu, gvilf, upzpfu, izlpj, psftlm, xmynm\npedyx (65)\nbjdabjc (37) -> vlanmkf, efqeq\nhbscmk (62)\nljmxejh (87)\nhbtqsvg (75)\nqcmpct (29)\nejltcdq (98)\njcpaww (353) -> yxmsys, nkriu\nrtttief (38)\nuqrnq (195) -> qadmk, xcyvydj\nouzekb (2145) -> wfvbe, wxzuuax, hbnsqgy\naputyq (6)\nsxukoj (65)\nwfvbe (101) -> robfda, ddzoj\nmftbcdj (196) -> cqehja, mhxta\nchcmfns (24) -> mfgah, lzzakg, eomcn, jztiyg\ngvywrbk (45)\nepepfyw (26)\neidmwnu (35522) -> vdrssx, tndlco, vmlot\nfxlfsh (202)\njwpff (60)\nrqtiz (44)\nnranogq (82)\nnbarshr (85)\nmdqgbt (27)\nxxgbjxv (106) -> tkvey, qbqdcm\nbpkcij (244) -> nexgmr, vlgck\nlrndlu (8)\nzxiyxq (49) -> oqhnzk, ralcb, cqhzg, msnnf, vckvw\nqtvfi (83)\nzciggcz (14) -> ktvnccr, hdlwni\nncovrwd (16)\nznbuoor (65)\ndnbpbn (92)\nioioi (187)\nffzmh (25)\nzxorl (73)\nvlgmhuv (251) -> smswtc, gmsvr, ekrfpes\ncyxpp (28)\ndlrbh (29) -> ejltcdq, prnxzn, ywdzbpm, zwapr\nppltn (111) -> psesrd, atjml, ictjzx, xwzln\nmfpfqpd (73)\nunbfqv (59) -> soypqq, xcdpn, kvxtoa, qekfk\ndlkhgy (73)\nhhsaqkq (23)\nkuolv (71)\nmoxllkd (86)\nthnxcx (81)\nlhfopq (49)\nzgfpv (95)\neiwbgkd (15)\nxoexpuv (100) -> eazmfh, xzwdltr, qvrhd\najptvhc (96)\noxyxxv (273)\nfcila (60)\nbfqgrx (24)\ncuopz (15)\nernwmw (15)\nujdaj (85)\npxggj (92)\ntvtbrol (220) -> zxjpf, raimv\nhgirpmg (172) -> pzzlwt, lwkib\nsgfes (35)\naotrzhg (95)\nqzuanqw (125) -> vcikq, vhlyct\nwtlxw (95)\nopdas (84)\nlpzseo (201) -> fvcnod, aputyq, fchob\nqgaal (385) -> jlbihbs, dmpmj\nsfqps (18)\nnftuawm (77) -> unpta, mdqgbt, lwhlpt\ndjkfqo (50)\nhvjak (55)\nkextbaa (93)\nztzerr (38)\nshavjjt (71)\nvnpro (313) -> wjnhlj, uodsyhs, rwvns\nqvrhd (32)\nlnxnld (16)\nklicwmt (65)\nscueow (18)\ngbaxyxf (79)\nmslwcef (70) -> wwpgr, wvvuyku\neemtac (59)\nicdqutb (88)\nbataqig (78)\nxtcdf (6041) -> kphqbqy, wxpae, rdbxsg\natktbij (146) -> akgtc, xqdno, ioioi, soxeud, piplyy, nglegc, qzuanqw\nnqzhi (83)\nfegzjc (99)\nhcltwvs (81) -> wjkajc, lmbyxln, ukktl, epeudep\ndbaczh (107) -> sszivvi, xbuelds\nralcb (239) -> uyfpwa, rcuauq\nviojqk (83)\ngizpa (54)\ncdcpur (60)\nvjipe (17)\nnjbklo (50)\njhbzqh (98)\nuoscg (49) -> tgvowq, klicwmt\nmnlkn (91)\nxmzreq (5)\nbqvid (49)\nddzoj (7)\nmevgyhq (1309) -> wsmdf, tcbpl\nqwibwv (86)\nkzaee (29)\ntqlbxbk (206)\nkzgvdvy (470)\nzokhh (85)\nqvasqa (292) -> qtlyvzn, yfvrd\ncedcjza (221) -> fgouoot, bdbfwjx\nimmwi (50)\niqccq (48)\neppyen (238) -> hmxve, boobyu\nryemzzp (119) -> jupsbj, cjgfncf\nmpzhwng (52)\nlrvkkp (81) -> fmwgpf, pfkkpf\nxwrkqj (64)\nswiyje (10574) -> oycjqok, vnpro, xslcw\njgxxaah (344) -> yjvso, oaylpg, vnjtmkj, njfgh\nignnpoe (1037) -> tpuff, nzdua, giidwph\nljrzagq (104) -> aouceoo, jtcxzs, ldjcv\npusfms (30)\nevetqs (56)\ntuctl (87)\njxgoaqy (80)\nvnjtmkj (18)\nnmdgz (144) -> ypvmcm, ddaxciv\nzlfmdxz (12)\nyxmsys (57)\ntvctwy (50)\nwhehan (10)\ndagpv (786) -> gkgab, bjdabjc, rbgqim\nulxmu (34)\ndrpftnv (87)\nyzbem (29)\nraimv (35)\ncdstxnu (238) -> vngxw, pqnhasu\nlnbgem (25)\nlwzvpvx (53)\nakgtc (91) -> fqeguqv, clcdcgr, pzozfud\noolchg (202) -> rvjfiqd, pyhrwp\neajgpo (51)\nwxzuuax (103) -> ackza, kimuc\nmgzhv (5)\nsggqt (80)\ntyueun (58)\nneagw (34)\ncbxzq (48)\nzranvyg (50)\nmsgckc (406)\ngohnrwt (75)\nksfylmr (149) -> dmzvtf, xfzgdj\nlwisav (43)\nktvdag (393) -> zowwvl, yhrhiyx, idsfdf, zyukres\nvlbvqd (80)\nnwzrdt (150) -> tvctwy, immwi\ntptuf (28) -> hbxquoz, zmkno, gvtpttw, tbsrvbd\nzcslj (17)\nqrtdqj (12) -> cwxpvg, jtmbf\nsvcgs (140) -> jpxxgq, scueow\nfdxcyr (75)\nyiihd (24)\nbkyrrr (7)\nizlpj (68) -> nukmm, kalidnk\nvopvppg (34)\nossumc (18)\nfduhgla (56)\neggpdp (73)\nyhkfv (42)\nxywrttu (285) -> peiiks, noymo, chttax, knnre\ndsgvsd (138)\nidsfdf (224) -> mgzhv, rsqkxid\nloyiwus (182) -> bilrn, cyfkfo\najcedg (88)\ndyhpnf (75)\njtnoqx (65)\nptzhczg (45)\nmewzj (5)\nfuiwxoi (50)\nunnwuw (53)\nhnuakg (129) -> pbvhscz, msgckc, qpjosh, fhcjbaf, bhznh, pktrea\npfnew (14) -> sygqg, hkawy\ntptuy (44)\nakrhumm (86)\nyfoeg (30) -> dyibh, lrvcwu\nbwhot (57)\nyejlp (54)\nfgouoot (54)\neltxjp (13) -> istfw, zxiyxq, xxkbo\nkimuc (6)\ndkdttm (78)\ndsfeott (74)\nxdyhig (134) -> ckhdiiu, djkfqo, qxmwtlw\nscgjs (30)\nhlpvxds (15)\nlipveyk (25)\nbqqbtuu (67)\nahdjfhr (90)\neomcn (83)\nmmmqt (90)\nhdlwni (53)\nvffjkzp (8) -> kjmhqa, oreple, ttquuz\nhdptwar (34)\nqihaqhe (81)\nznksd (40)\nmjhuaca (48) -> hbtqsvg, gohnrwt\nmzrwlw (65)\nrnoivcb (96)\nyctfou (254) -> hxftzw, sgfes\nwgzdoiy (81)\ntfrqfm (99)\nacxxafw (564) -> eevawfz, cfnxaxh, ktrpmw, axrvqtb\njbnhonh (86)\ndhsqdfu (77)\nkjkxg (39)\nwucysx (319)\ndcpesz (87)\ngkeumf (83)\nydhtgb (50) -> xdgvik, uyxck\ntymjf (59)\ntsvpfin (32)\nrbusgi (5) -> lwmlmt, ipzdtst\nnnxgmqu (236) -> zrdvbl, ebacdan\nfhcjbaf (253) -> gmgscd, eajgpo, ibdxfru\nnjfgh (18)\nzvoxbcv (370) -> dixbd, uvkiha, yfoeg, svcgs, xdgfor, jlxfw, mleek\npqkwc (8)\nvkvdnam (68)\njqiyk (67)\nmypnwe (60)\ntcbpl (66)\nendts (79)\nkbmkrfb (270) -> iittzki, cryim\najafdiv (38) -> tymjf, xagcn, pebqce\npnslx (49)\nzuahdoy (14) -> gsqcet, mfzpvpj, ygmtia, nvold\nkukwwup (12)\nlpjyqfd (95)\najllsx (77)\naxrvqtb (160) -> caqpd, slgdz, vjhxwan, ncovrwd\nouslph (84)\ncqehja (38)\ncqmnmp (82)\noeddmx (56)\ntpuff (36) -> deizqho, dgcaijl, dwnqi\ngvilf (82) -> jvvrtrv, gizpa, qvrlbp\npyozvr (68) -> mitlye, lavsfcf, yxtmb\nrysqk (161) -> yjslt, ernwmw, jqyrqjo\npiplyy (159) -> iirqqkc, gbshnls\nfdpan (26)\nxjvzuax (73)\nrsqkxid (5)\ntihmp (58) -> cjamvug, ouslph, tzvye\nvfclsgp (53)\nirduh (33) -> nnnkqg, fdxcyr\nmbkcx (163) -> lsdlhcv, flmcu, xphjp\nvptwcy (1561) -> pnslx, lhfopq\nddlrr (99) -> dlrbh, iothmz, rtrbpze\nfoooidm (10) -> btvpas, yykvyt\nxuenhje (83)\noreple (35)\nzyukres (88) -> hcason, wwzmxpr\nqsxkcs (23)\nyjcjjwh (256) -> ouszgvx, boydbo\nwopanki (17)\ntxtsesn (39)\nlgooafx (67)\ncsjtsvu (231) -> zvkhqpa, leoalr\ntkvey (60)\nbkoard (97)\nlxdnpno (28) -> eykrgl, ptrqmgy, ozkxsak, seehj\nkyqidwo (124) -> kiuysw, hbscmk, lpxod\nzirepil (69)\nokkry (64)\nqogcxh (59)\nxbxsa (98)\nxxlmsg (29)\nxmdpfk (304)\nncxfef (89)\ndrxfuqd (82) -> fyqrdda, lcldjzf\nntnmzjx (30) -> dcpesz, tuctl, itnlnpw, fvoilc\nwwhwhlh (49)\nnkriu (57)\nbhgzfhp (31) -> kzgvdvy, oasngox, wqwckx\neughxu (89)\nzzxvkw (175) -> zvysgi, xnuiprc\nzcxajvs (47)\nfzffa (15)\nmfzpvpj (604) -> ktrbnu, cmsfw, xjitfcr, nrukeq, tfofygr, bcknuwr\nazchhq (38)\nfjterk (52)\nhbummr (98)\njsezaqz (84)\nqbsauln (555) -> vpodq, prqstpz, hxyfrr\norvqjsu (13)\nqomla (67)\nnmgdw (20)\ntziwukl (71) -> iknffv, lzpis, ktlvla\ntlfhbr (320) -> kwnsatz, njbklo\nrbbrmfz (58) -> mfpfqpd, yjhyb, eggpdp\nqzhlkec (32)\nemndhg (37)\ngdmsj (345) -> rvzbog, lrvkkp, lpzseo, dotyah\nrfzkn (206) -> slgoac, qayhfqp\nkaswwui (67) -> woioqeb, hekaxal, zpmslbn, lsckdh\nmqreu (68)\nwhrgexw (38)\napyvj (52) -> zxorl, cxndnyb\nxagcn (59)\nbexgw (75)\ntewpgq (35)\nctsij (83)\ncrqmnp (180)\nvckvw (199) -> otrnol, jhbzqh\nbfmwj (61)\nfdtrij (62)\ngoxwq (42) -> tyvrby, uqrnq, hyhkb, pukgrz, xivfthm, armoelp, zzxvkw\nfosawx (53)\nbzujyh (57) -> dbyss, attvd, hiunh, suvnzd\nwcbktof (96)\nvkmmhj (83)\nqglqj (361)\nfqeguqv (32)\nfwtbaig (25)\nxjitfcr (237) -> kukwwup, zlfmdxz\nlpqyotn (873) -> qdufhxj, zciggcz, atfeuv\nwhckqd (65)\nfuxir (23)\naajml (79)\nzrvyll (53)\nwvvuyku (83)\ntwemenc (56)\nlpdfnmm (202)\nckhdiiu (50)\nrvvvy (196) -> vpapi, qkqjab\nibdxfru (51)\nadrfscr (87)\ngdoxoc (27)\ncaqpd (16)\ndkpebdn (75) -> wngzj, qmkrh, hbummr, dquww\nfrdghyw (15)\nvvfeefv (20) -> hbobj, zhhic\ndplsxrs (37)\ngrvssv (210) -> aslxei, lnbgem, lipveyk, ffzmh\nrevazr (89)\nlcldjzf (82)\nispkg (41)\njlbihbs (41)\nvlvwpx (92)\ngvojneq (81)\nuwlec (81)\nqkqjab (28)\nsedsofn (91)\nfqayp (336) -> zjefgu, rouhc, oqzhbrd\ndpoddhi (73)\nacddfq (175)\nfjhroj (93) -> tvjoswt, vlvwpx, ssqksg, pxggj\nooztkre (92)\nmbtopdb (75) -> tqifa, mdupi\ncjgfncf (32)\nwjkajc (49)\nyikly (84) -> txelahj, qihaqhe\nrlafz (62)\naouceoo (31)\nkomzqjf (82)\nvlycnj (246)\ndwnqi (35)\nlgpewn (62)\nphshj (541) -> owdiwh, dustv, ljrzagq, ioobi\npeegv (29)\nuzbucf (49)\nrbgqim (7) -> gvywrbk, ptzhczg\ndixbd (26) -> bexgw, dyhpnf\ncijsvr (319)\nnlnmv (364)\nhkawy (62)\ntuylou (21)\nyvhgp (71)\njztiyg (83)\njrphokq (69) -> lxeit, szbbr, wgquj, rvvvy\nfqzmaf (75)\nqybci (176) -> hrcrej, eiwbgkd, hlpvxds, cuopz\nlwhlpt (27)\nsszivvi (9)\nanazl (21)\nlavsfcf (54)\nxaryc (91)\nmldaqph (21)\neihvaj (222) -> dhibnu, gtzfc\nftfgyt (86)\nvmsxkt (18)\neykrgl (74)\nipkim (209) -> fosawx, zrvyll, rguicjs\nqdcbmq (35) -> ignnpoe, acxxafw, vfsoty, vlgmhuv, igdqv, bdrgk\nhrcrej (15)\ntgvowq (65)\nlelkjof (81)\nmyunfyp (1504) -> uzbucf, wwhwhlh\nfprcrwl (45) -> yjcjjwh, zzqucm, lopzzu, vcnhk, obtxdw, tlfhbr\nmfvzrxh (40) -> gizfx, vkxicq, lzckaxj\natjml (18)\nvcikq (31)\nlzzakg (83)\nhxftzw (35)\nfrbjm (77)\npfluh (126) -> zlrcjv, gyibmx\nmkthfc (59)\nhkzcxkb (93)\nywefb (63)\niknffv (99)\nolugc (1992) -> nevpxb, oflnmu, vffjkzp\nsdamh (41)\nyfvrd (32)\nktays (20)\nxdgvik (73)\nxcyvydj (18)\neiqfjh (8516) -> atktbij, kaswwui, jyavyin\nitqaixc (28) -> gduajbd, vtomrxb, ajcedg\nmuzit (92)\ntzpulfw (167) -> jykqgm, fzffa\nkalidnk (88)\nushlvth (14) -> cdstxnu, nlnmv, cgwtrf\nhvzzdv (731) -> wsbkos, dbaczh, mdbdbii\njlxfw (16) -> vlbvqd, lnezc\nowdiwh (15) -> mnlkn, xaryc\nsygqg (62)\nekrfpes (257) -> dpoddhi, xjvzuax\nlixdk (57)\nxivfthm (65) -> ctsij, qtvfi\nlddpvs (57)\nlzpis (99)\nscxhcfn (35)\naxhgfu (142) -> hdptwar, eyttrr, vopvppg\ncxhafp (96)\nqtsfwnv (23)\nrecld (128) -> fxtoawh, mewzj\nnrpfq (13)\nzovgme (66)\nsxyoft (92)\nlwkib (13)\nvcnhk (246) -> ohxkp, neqztkt\nhvqcxdx (20)\noqyze (91)\nqrzjxx (75) -> znbuoor, sxukoj\neebyqij (17)\njpodlkc (95)\nnnnkqg (75)\ntvhttj (37)\nsintjhl (1076) -> bthky, ggapah, lpdfnmm, foooidm, kjtclbz, euhterr, fxlfsh\nobtxdw (244) -> ynnefdj, icdqutb\ndustv (87) -> gsqag, chrpwez\nnwnlk (221) -> tewpgq, scxhcfn\nitokb (14) -> yejlp, ysofby\nvmlot (5960) -> ruqwlqw, lpqyotn, lwhywr\nbhznh (226) -> iaekvbf, lmyoms\nktrpmw (82) -> dkbunrw, mdhmr\noizyf (97)\njtzidh (39)\nvtkkzt (153) -> nrpfq, hjddl, qdqund, orvqjsu\nhxyfrr (73) -> dtnlyc, ghojvw\nxqdno (23) -> komzqjf, dwklefi\nmtwod (71)\nchttax (11)\ndwrnlgg (26)\nprnxzn (98)\npgerst (361)\nqmvje (169) -> wkpka, eaxthh\nspyey (205)\nixtywxj (34)\ntdaxn (17)\nozafzy (405) -> lxdnpno, yctfou, oolchg, gylpg, putuzm, apunr, ylavfs\nsoxeud (165) -> mgigu, ozynj\nclcdcgr (32)\nfgfjwf (62)\nxwzln (18)\nmdupi (99)\nhltkocy (1631) -> acddfq, rbusgi, kpbld, biigs\nqwmqr (30)\nvngxw (63)\nhqdse (95)\nanirf (83)\nygmtia (26) -> kbmkrfb, soweon, ywlwppw, chcmfns, atngvo, qvasqa\nvyjyqm (72) -> tgzin, moxllkd, qwibwv, akrhumm\ndpily (299) -> moldik, fuiwxoi\ngcvvr (258) -> lnxnld, pthmd\nurcmmqc (52) -> qkxpxwf, jbnhonh, ftfgyt\ndhevr (63) -> gfofc, rtttief, whrgexw, azchhq\nywdzbpm (98)\ntrwmicb (151) -> anirf, nqzhi\nahhobtu (68)\nrguicjs (53)\niztqqg (4709) -> unhzsf, qbsauln, ddlrr\nvlanmkf (30)\nmiyzuba (93)\nwwfsfm (35) -> rtpusqx, txuqarh, hltkocy, olugc, xfqbdst, trkar\nzloyhge (11)\nfxmyl (97)\nwnrch (34)\nhyvee (28)\nnevpxb (57) -> dxzrqa, txexr\narmoelp (81) -> fqzmaf, ztowevm\nnrukeq (261)\nhvvlhe (55)\ntqlxusp (83)\noasngox (242) -> qdlqy, cfwjs, agvef, quayoh\nrxogya (20)\nvtomrxb (88)\nekgzga (92)\nzjefgu (139) -> jfzpr, bataqig\nyskugrc (89)\npbvhscz (112) -> pubnb, xbxsa, hicwq\nzidgf (93)\nbdbfwjx (54)\nlmyoms (90)\nnalot (38)\nzsgntfb (58)\nsjdcml (122) -> vbjrc, yptmfb, iqqovp\nqpqwnti (26)\nflsqqw (97) -> vtnddde, eihvaj, vzmxrqs, upnvuf, tziwukl, dzmst, rfzkn\ncgwtrf (79) -> hqdse, wtlxw, vfmuj\nofwwstm (139) -> fgont, hvqcxdx\nrltnd (50)\npqnhasu (63)\nenumule (73)\ndzmst (332) -> wdcers, cxcibc, ysphvx\nuegzzq (511) -> cccizey, yikly, vlycnj\ncwfhyvp (81)\ngvjwray (1402) -> obsfib, mftbcdj, kozpul, ahjzy\ngowpln (44) -> nnwvn, wgzdoiy, cidfsbr, psnhw\ngxxrr (1571) -> ouzekb, qbkxno, sintjhl, gxbbwl, gvjwray\navsqfag (151) -> hzqvef, frdghyw\nqhqqpj (88) -> xpvhb, hvjak\nnetob (153) -> lrndlu, pqkwc\nlvhelk (70)\ndbcevsv (56) -> hnuakg, xiiwbs, fprcrwl, jckhii, azowpjp\nigdqv (40) -> xvffp, fmtveft, apjgsl, tbnqbh, xdyhig\nictjzx (18)\niemfk (56)\nssqksg (92)\ndkbunrw (71)\nknnre (11)\ncjamvug (84)\nmranee (81)\ndjorufg (56)\nyfxlqu (2145) -> qhosh, nxphajd, slbbde\ngiidwph (63) -> yztbahh, xphdneu\nzwrqsm (23)\ngsqcet (797) -> ptaorpg, oxyxxv, mbtopdb, eykvd, ksfylmr\nhbobj (80)\ntmonbh (41)\nrsxned (152) -> dixsodp, tvtbrol, viosv, ksqwp, gcvvr\npqsufx (78) -> acfuxo, ngicn\ntzvye (84)\nputuzm (324)\nnixzhb (96)\njpxxgq (18)\nrptcitf (97)\nkwnsatz (50)\ndkktvs (87)\nlklfzh (52)\nhixbkj (128) -> bxiroo, zverm\neksvho (42)\nhmxve (70)\nfaysq (69)\nkgifnwx (91)\nvtnddde (284) -> mldaqph, ziwqdh, rbbtkcf, bnuqzi\nypvmcm (43)\nkumzgqd (5132) -> szphud, fqayp, gdmsj\nvlqjczn (48) -> dodxloj, lixdk\nqxmwtlw (50)\niqqovp (80) -> fdtrij, scocac, fgfjwf, rlafz\ngwknw (12)\nclmdi (71)\npwqlro (273) -> ywefb, olnmey\nuvkiha (8) -> xspgdc, cdgpv, djorufg\njpfcy (80) -> gvrmz, yeano\nylswd (23)\nynunzdd (46) -> recld, pfnew, hofab, dsgvsd, pqsufx\nfnnaq (78)\ndbyss (57) -> ljmxejh, drpftnv, dwfoa\nleoalr (30)\niatxfon (43)\nbnuqzi (21)\nidmwnxg (130) -> wzlfky, wnrch\nfyqrdda (82)\njotjtdm (67)\nfvcnod (6)\nhepoax (467)\nboobyu (70)\nkjmhqa (35)\neazmfh (32)\nvrupz (79)\nqvrlbp (54)\nvtdovx (5)\ndcxivs (26)\nupzpfu (78) -> tqlxusp, xuenhje\npthmd (16)\nfikxbn (69) -> uznrazj, lddpvs\nextlmn (93)\ncxktgip (39)\nftqnpo (23)\ngawxacm (67)\ngylpg (134) -> jpodlkc, zgfpv\nlxryza (20) -> avfse, mtinmla\nwdcpr (96)\nfmrcz (73)\nfxtoawh (5)\nlsdlhcv (66)\newdwpeh (64)\nehqmjeh (48)\nlrymy (57)\ndvjdohw (875) -> pzmnutm, ydhtgb, xoexpuv, bbycv\nneqztkt (87)\nuyfpwa (78)\nwwzmxpr (73)\nfleler (140) -> cbxzq, ehqmjeh\nbxoyo (68)\nvvxbkb (111) -> gctir, gkeumf\ndhwffod (345) -> vmsxkt, lonjduc, ucodfmw\nynoxdm (49)\npxbps (29)\nhzqvef (15)\ndixsodp (136) -> ajllsx, pzmvwhg\ncbhxz (39)\ncqhzg (201) -> xtaoa, gcoqii\nziwqdh (21)\ndsgkaw (319)\nvdrssx (47) -> myunfyp, jdsfjkf, rsxned, trnzjj, vhzfk, zvoxbcv\neuhterr (144) -> daznpq, qcmpct\nfchob (6)\nijbcav (66)\nozynu (84)\nysyjko (204) -> yddky, cocpc\nxnuiprc (28)\nvlmtckh (82) -> cqmnmp, nranogq\nwxlrte (77) -> eavzu, urarg\nxmynm (13) -> nazazve, tmeconw, frbjm\nnpzfhkv (207) -> oeddmx, iemfk\nghojvw (98)\nkyfjsn (78)\nprqstpz (236) -> xamoxye, buhyicq, zloyhge\npwfoj (35) -> oqyze, ysvmli, ftkcs, sedsofn\nnpejje (42)\naavyxnd (658) -> mjatmj, cblanil, tzpulfw\nwzcrtz (79)\nslgdz (16)\neevawfz (26) -> tfrqfm, fegzjc\nqdqund (13)\nnctisdd (144) -> txtsesn, xuuboqp, cxktgip, kjkxg\nbpuuio (26)\nopcszf (55)\nszbbr (96) -> xkwuq, rqeosyf\nbxakn (28) -> zcxajvs, tkzjfkq\nkjjzo (74)\nyeano (60)\ncdgpv (56)\nhcason (73)\nddaxciv (43)\nmeecdj (21)\nyxtmb (54)\nbljxv (134) -> nbppbiy, npyxpyc\nfuusxze (84)\npzozfud (32)\nwkpka (5)\njykqgm (15)\nsjrdmho (44)\nghicb (35)\ngcoqii (97)\nvlgck (24)\nqkxpxwf (86)\nwdmqsw (77)\nttquuz (35)\ncccizey (146) -> rltnd, oooma\ngmsvr (315) -> klpjjvg, tptuy\nxfzgdj (62)\nrakwfhk (57)\ndryrgj (855) -> ljhtnf, nftuawm, trczg\nbebujq (66)\nwxscr (41)\nlqsjdu (195) -> niajghy, ispkg\ndhibnu (73)\noaylpg (18)\ngctir (83)\nrdbxsg (94) -> xptngy, tqlbxbk, rysqk, wwjtzuf\nudaqlo (4921) -> csjtsvu, nwnlk, iljnbiz, lbjvtj\niaekvbf (90)\ntesstj (84)\nrouhc (163) -> bebujq, ijbcav\napunr (135) -> xoudko, eckrps, oyjliid\ndgcaijl (35)\nmuaiipe (33) -> yskugrc, eughxu, revazr\nxluprc (1053) -> orxan, hvzzdv, hcphjb, sjdcml, ushlvth, xtnctiv, qfkkwc\niirqqkc (14)\nrhprie (26)\nxcdpn (80)\ngduajbd (88)\nniajghy (41)\ntjhrdl (27)\ngtosn (90)\nvfsoty (974) -> vlqjczn, hixbkj, lxryza\nknmyjju (70)\norxan (461) -> cvpimoj, ajafdiv, dhevr\nvhzfk (1053) -> fikxbn, wbpqtn, puqymd\ngbshnls (14)\notkntx (37)\nsuvnzd (126) -> nixzhb, wdcpr\nkpbld (23) -> dycdyx, gmlyf\npzzlwt (13)\niothmz (293) -> wekywpz, xwrkqj\nmoldik (50)\nistfw (362) -> vvxbkb, hcltwvs, oexuzjy, wonidli, rbbrmfz, qnfxh\nnazazve (77)\nwqwckx (98) -> uvekre, eprwoep, qngdf, hkzcxkb\nfasqn (68)\nxoudko (63)\nlopdq (2026) -> goxwq, vptwcy, geexh, dvjdohw\nzowwvl (75) -> vfclsgp, unnwuw, lwzvpvx\nqmkrh (98)\ndeizqho (35)\nsuqkyqc (945) -> wcbktof, lrcfe, ajptvhc, cxhafp\npebqce (59)\nlocrn (3877) -> klgfmp, mbljzmm, ynunzdd\nxpvhb (55)\nfvoilc (87)\njssuppj (61)\ntrkar (1416) -> wxlrte, ryemzzp, ppltn, irduh, tqbqd\naroct (38)\nzsrao (53) -> kzaee, xxlmsg, peegv, pxbps\ntvjoswt (92)\nebacdan (71)\nacfuxo (30)\nrqeosyf (78)\nyjslt (15)\nrobfda (7)\nqpjosh (90) -> gbaxyxf, vrupz, vahqnf, comkkpi\nrvvrxj (38)\nxhumie (166) -> bkyrrr, sbljlc\nktlvla (99)\njfzpr (78)\ngkgab (97)\nnsssqpp (68)\nyqhlag (31)\nyhrhiyx (94) -> zwngnd, lvhelk\nbiigs (135) -> ktays, nmgdw\njdbyghb (268) -> chrya, gwknw\nzverm (17)\nmwtcdof (23)\nacfeme (95)\nepeudep (49)\nyztbahh (39)\nktrbnu (113) -> dsfeott, qiesity\nveojykb (70)\ndmzvtf (62)\noflnmu (113)\nviosv (38) -> fuusxze, tesstj, gcqeyqn\nukktl (49)\nbprczj (79)\ntgzin (86)\nwwpgr (83)\nhhoafz (90) -> sggqt, jxgoaqy\npzmnutm (186) -> rxurj, xmzreq\nxlhtsn (28)\nlpxod (62)\nmhxta (38)\nvjhxwan (16)\ncqjena (23)\nacuce (5)\nbtvpas (96)\nccodif (31)\nljhtnf (66) -> ylswd, hhsaqkq, qsxkcs, zwrqsm\nefozgh (34)\nnukmm (88)\nwekywpz (64)\nmgigu (11)\nasyku (72) -> ozynu, jsezaqz\nqiesity (74)\nwwumy (38)\nlopzzu (180) -> mypnwe, cdcpur, pjpcyya, ygqakoh\nlonjduc (18)\nfxzmev (110) -> qomla, jqiyk\nqekfk (80)\ntdyqxhp (116) -> mcwhhll, ooztkre\ngksdobg (23)\nojhkvb (62)\nflwucgf (84)\nunpta (27)\nviaivun (118) -> ewdwpeh, okkry\nbysqw (56)\nxzwdltr (32)\nhcphjb (566) -> crqmnp, xhumie, vvfeefv\ngyibmx (95)\nolnmey (63)\ngmgscd (51)\nwlxgck (39)\nqbxat (9)\namcrzt (329) -> ggrzmj, zovgme\nuodsyhs (88) -> cvyof, tsvpfin\nliedl (35)\nxlajoju (29)\nqbkxno (1953) -> ofwwstm, qmvje, uoscg\nsbljlc (7)\nmdbdbii (69) -> cdhed, fnsol\nqfhkrk (106) -> mshtzph, ujdaj, zokhh\nxdgfor (42) -> jotjtdm, lgooafx\nuznrazj (57)\nzjdnsr (20)\nepjrvvp (57)\nayrzzu (6)\nlzckaxj (79)\ntfofygr (219) -> jmfvn, anazl\nzwapr (98)\ndlhnqsu (6)\ntbnqbh (41) -> cwfhyvp, gvojneq, kuyofu\naslxei (25)\nsnwwnem (30)\nqaxlov (74)\nipzdtst (85)\noxada (636) -> ipkim, qmghpah, gowpln\nyddky (53)\ncblanil (197)\nanujsv (71)\nwkvfo (177) -> muaiipe, nctisdd, tdyqxhp"

  def parseInput(str: String): List[String] = {
    str.split("\n").toList
  }

  def reduce(n: Node, forest: List[Node]): List[Node] = {
    val f = forest.filter(p => p.name != n.name)
    f.map(p => {
      if (p.done) {
        p
      } else {
        if (p.children.contains(n.name)) {
          new Node(p.name, p.weight, p.children, n :: p.tree)
        } else {
          p
        }
      }
    })
  }

  def findRootUnballance(n: Node): Option[Node] = {
    n.ballanced

    Some(n)
  }

  def findDoneNode(l: List[Node]): Node = {
    l.find(p => p.done).get
  }

  def run(forest: List[Node]): List[Node] = {
    var l = forest
    while (l.size > 1) {
      l = reduce(findDoneNode(l), l)
    }
    l
  }


  def main(args: Array[String]): Unit = {
    var l = parseInput(testInput)
    var forest = l.map(s => Node(s))
    forest.foreach(println)

    println()

    //val t = findDoneNode(forest)
    //forest = reduce(t, forest)

    forest = run(forest)
    forest.foreach(println)

    // -------
    l = parseInput(input)
    forest = l.map(s => Node(s))
    forest = run(forest)
    forest.foreach(println)

    // ---
    forest.head.traverse
  }

}
