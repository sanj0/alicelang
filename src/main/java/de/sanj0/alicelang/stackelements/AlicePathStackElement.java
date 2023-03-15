package de.sanj0.alicelang.stackelements;

import de.sanj0.alicelang.AlicePath;
import de.sanj0.alicelang.StackElement;

public class AlicePathStackElement extends StackElement<AlicePath> {

    public AlicePathStackElement(final AlicePath value) {
        super(value);
    }

    @Override
    public StackElement<AlicePath> dublicate() {
        return new AlicePathStackElement(getValue());
    }
}
