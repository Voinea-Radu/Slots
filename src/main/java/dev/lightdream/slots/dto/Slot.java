package dev.lightdream.slots.dto;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.dto.Item;
import dev.lightdream.api.dto.Randomizable;
import dev.lightdream.api.dto.Reward;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class Slot implements Randomizable {

    public Item item;
    public int chance;
    public Reward reward;

    public void win(User user) {
        if (reward == null) {
            return;
        }
        reward.win(user);
    }

    @Override
    public int getChance() {
        return chance;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "item=" + item +
                ", chance=" + chance +
                ", reward=" + reward +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return Objects.equals(item, slot.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }
}
