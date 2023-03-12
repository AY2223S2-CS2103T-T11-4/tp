package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACKUP;

/**
 * Parses input arguments and creates a new {@code LoadCommand} object
 */
public class LoadCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code LoadCommand}
     * and returns a {@code LoadCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoadCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_BACKUP);

        Index index;
        try {
            String indexString = argMultimap.getValue(PREFIX_BACKUP).orElse("");
            index = ParserUtil.parseIndex(indexString);
        } catch (IllegalValueException | IllegalArgumentException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadCommand.MESSAGE_USAGE), ive);
        }

        return new LoadCommand(index);
    }
}
