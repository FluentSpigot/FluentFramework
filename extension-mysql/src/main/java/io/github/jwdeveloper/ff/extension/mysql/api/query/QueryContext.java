package io.github.jwdeveloper.ff.extension.mysql.api.query;

import io.github.jwdeveloper.ff.extension.mysql.implementation.executor.SqlQueryExecutor;
import io.github.jwdeveloper.ff.extension.mysql.implementation.mapper.SqlQueryMapper;
import io.github.jwdeveloper.ff.extension.mysql.implementation.query.SqlQueryModelTranslator;
import lombok.Getter;

@Getter
public class QueryContext
{
   private final QueryModel queryModel;
   private final SqlQueryExecutor queryExecutor;
   private final SqlQueryMapper queryMapper;
   private final SqlQueryModelTranslator queryModelTranslator;

   public QueryContext(QueryModel model,
                       SqlQueryExecutor executor,
                       SqlQueryMapper mapper,
                       SqlQueryModelTranslator queryModelTranslator)
   {
      this.queryModel = model;
      this.queryExecutor = executor;
      this.queryMapper = mapper;
      this.queryModelTranslator = queryModelTranslator;
   }
}
