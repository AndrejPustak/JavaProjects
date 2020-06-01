package hr.fer.zemris.java.hw05.db;

import java.util.List;
import java.util.Objects;

/**
 * Class QueryFilter is an implementation of IFilter. It has a method which checks
 * if the given record satisfies one or more conditional expressions
 * @author Andrej
 *
 */
public class QueryFilter implements IFilter {
	
	/**
	 * List of conditional expressions
	 */
	List<ConditionalExpression> list;
	
	/**
	 * Constructor for the QueryFilter
	 * @param list list of the conditional expressions
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = Objects.requireNonNull(list);
	}
	
	/**
	 * This method checks if the given record satisfies all of the conditional expressions from the list
	 * @param record record you wish to check satisfies the expressions
	 * @return True if the record satisfies the expressions, false otherwise
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		
		for(ConditionalExpression expr : list) {
			if(!expr.getComparisonOperator().satisfied(
					 expr.getFieldGetter().get(record), expr.getStringLiteral() )){
				return false;
			}

		}
		
		return true;
	}

}
