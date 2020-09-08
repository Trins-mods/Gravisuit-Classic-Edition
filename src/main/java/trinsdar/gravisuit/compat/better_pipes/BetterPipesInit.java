package trinsdar.gravisuit.compat.better_pipes;

import flyingperson.BetterPipes.BetterPipes;
import flyingperson.BetterPipes.compat.wrench.IC2WrenchProvider;
import flyingperson.BetterPipes.compat.wrench.IWrenchProvider;

public class BetterPipesInit {
    public static void init(){
        int ic2 = -1;
        for (int i = 0; i < BetterPipes.instance.WRENCH_PROVIDERS.size(); i++) {
            IWrenchProvider provider = BetterPipes.instance.WRENCH_PROVIDERS.get(i);
            if (provider instanceof IC2WrenchProvider){
                ic2 = i;
                break;
            }
        }
        if (ic2 != -1) {
            BetterPipes.instance.WRENCH_PROVIDERS.remove(ic2);
            BetterPipes.instance.WRENCH_PROVIDERS.add(new GravitoolProvider());
        }
    }
}
