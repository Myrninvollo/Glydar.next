package org.glydar.protocol.codec;

import io.netty.buffer.ByteBuf;

import org.glydar.api.item.Consumable;
import org.glydar.api.item.Weapon;
import org.glydar.api.item.Equipment;
import org.glydar.api.item.Item;
import org.glydar.api.item.ItemUpgrade;
import org.glydar.api.item.ItemSubtype.ConsumableSubtype;
import org.glydar.api.item.ItemSubtype.WeaponSubtype;

public final class ItemCodec {

    private ItemCodec() {
    }

    public static Item readItem(ByteBuf buf) {
        byte typeId = buf.readByte();
        byte subtypeId = buf.readByte();
        Item i;
        switch (typeId){
        	case 1:
        		i = new Consumable(ConsumableSubtype.getById(subtypeId));
        		break;
        	case 3:
        		i = new Weapon(WeaponSubtype.getById(subtypeId));
        		break;
        	default:
        		i = new Item(typeId, subtypeId);
        }
        buf.skipBytes(2);
        i.setModifier(buf.readUnsignedInt());
        i.setMinusModifier(buf.readUnsignedInt());
        i.setRarity(buf.readByte());
        i.setMaterialId(buf.readByte());
        i.setFlags(buf.readByte());
        buf.skipBytes(1);
        i.setLevel(buf.readShort());
        buf.skipBytes(2);

        ItemUpgrade[] upgrades = i.getUpgrades();
        for (int j = 0; j < upgrades.length; ++j) {
            upgrades[j] = readItemUpgrade(buf);
        }
        i.setUpgrades(upgrades);

        i.setUpgradeCount(buf.readUnsignedInt());
        return i;
    }

    public static void writeItem(ByteBuf buf, Item i) {
        buf.writeByte(i.getTypeId());
        buf.writeByte(i.getSubtypeId());
        buf.writeZero(2);
        buf.writeInt((int) i.getModifier());
        buf.writeInt((int) i.getMinusModifier());
        buf.writeByte(i.getRarity());
        buf.writeByte(i.getMaterialId());
        buf.writeByte(i.getFlags());
        buf.writeZero(1);
        buf.writeShort(i.getLevel());
        buf.writeZero(2);
        ItemUpgrade[] upgrades = i.getUpgrades();
        for (int j = 0; j < upgrades.length; ++j) {
            writeItemUpgrade(buf, upgrades[j]);
        }
        buf.writeInt((int) i.getUpgradeCount());
    }

    public static ItemUpgrade readItemUpgrade(ByteBuf buf) {
        ItemUpgrade u = new ItemUpgrade();
        u.setxOffset(buf.readByte());
        u.setyOffset(buf.readByte());
        u.setzOffset(buf.readByte());
        u.setMaterial(buf.readByte());
        u.setLevel(buf.readUnsignedInt());
        return u;
    }

    public static void writeItemUpgrade(ByteBuf buf, ItemUpgrade u) {
        buf.writeByte(u.getxOffset());
        buf.writeByte(u.getyOffset());
        buf.writeByte(u.getzOffset());
        buf.writeByte(u.getMaterial());
        buf.writeInt((int) u.getLevel());
    }

    public static Equipment readEquipment(ByteBuf buf) {
        Equipment e = new Equipment();
        e.setUnknown1(readItem(buf));
        e.setNeck(readItem(buf));
        e.setChest(readItem(buf));
        e.setFeet(readItem(buf));
        e.setHands(readItem(buf));
        e.setShoulder(readItem(buf));
        e.setWeaponLeft(readItem(buf));
        e.setWeaponRight(readItem(buf));
        e.setRingLeft(readItem(buf));
        e.setRingRight(readItem(buf));
        e.setLight(readItem(buf));
        e.setSpecial(readItem(buf));
        e.setPet(readItem(buf));
        return e;
    }

    public static void writeEquipment(ByteBuf buf, Equipment e) {
        writeItem(buf, e.getUnknown1());
        writeItem(buf, e.getNeck());
        writeItem(buf, e.getChest());
        writeItem(buf, e.getFeet());
        writeItem(buf, e.getHands());
        writeItem(buf, e.getShoulder());
        writeItem(buf, e.getWeaponLeft());
        writeItem(buf, e.getWeaponRight());
        writeItem(buf, e.getRingLeft());
        writeItem(buf, e.getRingRight());
        writeItem(buf, e.getLight());
        writeItem(buf, e.getSpecial());
        writeItem(buf, e.getPet());
    }
}
