package zmq;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestReqrepIpc
{
    @Test
    public void testReqrepIpc()
    {
        Ctx ctx = ZMQ.init(1);
        assertThat(ctx, notNullValue());
        SocketBase sb = ZMQ.socket(ctx, ZMQ.ZMQ_REP);
        assertThat(sb, notNullValue());
        boolean brc = ZMQ.bind(sb, "ipc:///tmp/tester2");
        assertThat(brc , is(true));

        SocketBase sc = ZMQ.socket(ctx, ZMQ.ZMQ_REQ);
        assertThat(sc, notNullValue());
        brc = ZMQ.connect(sc, "ipc:///tmp/tester2");
        assertThat(brc , is(true));

        Helper.bounce(sb, sc);

        //  Tear down the wiring.
        ZMQ.close(sb);
        ZMQ.close(sc);
        ZMQ.term(ctx);
    }
}
