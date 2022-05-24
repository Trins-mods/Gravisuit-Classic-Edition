package trinsdar.gravisuit.network;


/**
 * Created by brandon3055 on 23/4/2016.
 */
public abstract class MessageHandlerWrapper/*<REQ extends IMessage, REPLY extends IMessage> implements IMessageHandler<REQ, REPLY>*/ {

    /*@Override
    public REPLY onMessage(REQ message, MessageContext ctx) {

        PacketSyncObject<REQ, REPLY> syncObject = new PacketSyncObject<REQ, REPLY>(message, ctx) {
            @Override
            public void run() {
                reply = handleMessage(message, ctx);
            }
        };

        if (ctx.side == Side.CLIENT) {
            syncObject.addPacketClient();
        } else {
            syncObject.addPacketServer();
        }

        //TODO Find a way to handle replies (when needed) because this does not actually work
        return syncObject.reply;
    }

    public abstract REPLY handleMessage(REQ message, MessageContext ctx);*/

}
