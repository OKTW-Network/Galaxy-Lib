package one.oktw.galaxy.proxy.api.packet

import org.bson.codecs.pojo.annotations.BsonDiscriminator

@BsonDiscriminator
interface Packet {
    companion object {
        val dummy = DummyPacket()
    }
}
