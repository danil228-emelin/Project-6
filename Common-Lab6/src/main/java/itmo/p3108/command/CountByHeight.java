package itmo.p3108.command;

import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.OneArgument;
import itmo.p3108.exception.ValidationException;
import itmo.p3108.util.CheckData;
import itmo.p3108.util.CollectionController;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.util.Optional;

/**
 * Command CountByHeight,count elements with certain height,
 * don't work if collection is empty.
 * Has one parameter height
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CountByHeight implements OneArgument<Double> {
    @Serial
    private static final long serialVersionUID = 559988001L;
    private double height;

    @Override
    public String description() {
        return
                "count_by_height height:посчитать количество элементов с заданным возростом";
    }

    @Override
    public String name() {
        return "count_by_height";
    }


    @Override
    public String execute(Object argument) {
        if (argument instanceof Double height) {
            if (CollectionController.getInstance().isEmpty()) {
                throw new ValidationException("Collection is empty");
            }
            log.info(String.format("%s executed successfully", this.name()));

            return Long.toString(CollectionController.getInstance().getPersonList().stream().parallel().filter(x -> x.getPersonHeight().compareTo(height) == 0).count());
        }
        log.error(String.format("%s Wrong argument", this.name()));
        throw new ValidationException("Wrong argument for CountByHeight");
    }

    public Optional<Command> prepare(@NonNull String height) {
        boolean validation = new CheckData().checkPersonHeight(height);
        if (!validation) {
            throw new ValidationException("height isn't positive number");
        }
        this.height = Double.parseDouble(height);
        return Optional.of(this);
    }

    @Override
    public void setParameter(Double parameter) {
        height=parameter;
    }

    @Override
    public Double getParameter() {
        return height;
    }
}
