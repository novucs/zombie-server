package net.novucs.zombieserver.command;

import com.sk89q.intake.parametric.ParameterException;
import com.sk89q.intake.parametric.argument.ArgumentStack;
import com.sk89q.intake.parametric.binding.BindingBehavior;
import com.sk89q.intake.parametric.binding.BindingHelper;
import com.sk89q.intake.parametric.binding.BindingMatch;

public class Bindings extends BindingHelper {

    @BindingMatch(type = CommandResult.class, behavior = BindingBehavior.PROVIDES)
    public CommandResult getCommandResult(ArgumentStack context) throws ParameterException {
        CommandResult result = context.getContext().getLocals().get(CommandResult.class);

        if (result == null) {
            throw new ParameterException("No command result.");
        }

        return result;
    }
}
