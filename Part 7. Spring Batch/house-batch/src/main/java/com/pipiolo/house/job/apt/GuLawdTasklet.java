package com.pipiolo.house.job.apt;

import com.pipiolo.house.core.repository.LawdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

/**
 * ExecutionContext Data
 * 1. guLawdCd : 구 코드 -> 다음 스텝에서 사용할 값
 * 2. guLawdCdList : 구 코드 리스트
 * 3. itemCount : 남아있는 구 코드 개수
 */
@RequiredArgsConstructor
public class GuLawdTasklet implements Tasklet {

    private int itemCount;
    private List<String> guLawdCdList;
    private final LawdRepository lawdRepository;

    private static final String KEY_GU_LAWD_CD = "guLawdCd";
    private static final String KEY_GU_LAWD_CD_LIST = "guLawdCdList";
    private static final String KEY_ITEM_COUNT = "itemCount";

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext executionContext = getExecutionContext(chunkContext);
        initList(executionContext);
        initCount(executionContext);

        if (itemCount == 0) {
            contribution.setExitStatus(ExitStatus.COMPLETED);
            return RepeatStatus.FINISHED;
        }

        itemCount--;
        executionContext.putString(KEY_GU_LAWD_CD, guLawdCdList.get(itemCount));
        executionContext.putInt(KEY_ITEM_COUNT, itemCount);

        contribution.setExitStatus(new ExitStatus("CONTINUABLE"));
        return RepeatStatus.FINISHED;
    }

    private ExecutionContext getExecutionContext(ChunkContext chunkContext) {
        StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
        return stepExecution.getJobExecution().getExecutionContext();
    }

    private void initList(ExecutionContext executionContext) {
        if (executionContext.containsKey(KEY_GU_LAWD_CD_LIST)) {
            guLawdCdList = (List<String>) executionContext.get(KEY_GU_LAWD_CD_LIST);
        } else {
            guLawdCdList = lawdRepository.findDistinctGuLawdCd();
            executionContext.put(KEY_GU_LAWD_CD_LIST, guLawdCdList);
            executionContext.putInt(KEY_ITEM_COUNT, guLawdCdList.size());
        }
    }

    private void initCount(ExecutionContext executionContext) {
        if (executionContext.containsKey(KEY_ITEM_COUNT)) {
            itemCount = executionContext.getInt(KEY_ITEM_COUNT);
        } else {
            itemCount = guLawdCdList.size();
        }
    }
}
