package org.glydar.protocol.codec;

import io.netty.buffer.ByteBuf;

import java.util.List;

import org.glydar.api.geom.IntVector2;
import org.glydar.api.geom.LongVector3;
import org.glydar.api.item.Item;
import org.glydar.api.world.ChunkItem;
import org.glydar.api.world.ChunkItems;

public final class WorldCodec {

    private WorldCodec() {
    }

    public static ChunkItem readChunkItem(ByteBuf buf) {
        Item item = ItemCodec.readItem(buf);
        LongVector3 position = GeomCodec.readLongVector3(buf);

        ChunkItem chunkItem = new ChunkItem(item, position);
        chunkItem.setRotation(buf.readFloat());
        chunkItem.setScale(buf.readFloat());
        chunkItem.setUnknown1(buf.readByte());
        buf.skipBytes(3);
        chunkItem.setDropTime(buf.readUnsignedInt());
        chunkItem.setUnknown2(buf.readUnsignedInt());
        chunkItem.setUnknown3(buf.readInt());

        return chunkItem;
    }

    public static void writeChunkItem(ByteBuf buf, ChunkItem chunkItem) {
        ItemCodec.writeItem(buf, chunkItem.getItem());
        GeomCodec.writeLongVector3(buf, chunkItem.getPosition());
        buf.writeFloat(chunkItem.getRotation());
        buf.writeFloat(chunkItem.getScale());
        buf.writeByte(chunkItem.getUnknown1());
        buf.writeZero(3);
        buf.writeInt((int) chunkItem.getDropTime());
        buf.writeInt((int) chunkItem.getUnknown2());
        buf.writeInt(chunkItem.getUnknown3());
    }

    public static ChunkItems readChunkItems(ByteBuf buf) {
        IntVector2 position = GeomCodec.readIntVector2(buf);
        ChunkItems chunkItems = new ChunkItems(position);
        int length = buf.readInt();
        for (int i = 0; i < length; i++) {
            chunkItems.addItem(readChunkItem(buf));
        }

        return chunkItems;
    }

    public static void writeChunkItems(ByteBuf buf, ChunkItems chunkItems) {
        GeomCodec.writeIntVector2(buf, chunkItems.getPosition());
        List<ChunkItem> items = chunkItems.getItems();
        buf.writeInt(items.size());
        for (ChunkItem chunkItem : items) {
            writeChunkItem(buf, chunkItem);
        }
    }
}
