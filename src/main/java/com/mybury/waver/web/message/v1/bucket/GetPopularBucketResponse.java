package com.mybury.waver.web.message.v1.bucket;

import com.mybury.waver.domain.Bucket;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public record GetPopularBucketResponse(
    List<String> popularKeyword,
    List<BucketElement> popularList,
    List<String> recommendKeyword,
    List<BucketElement> recommendList
){

    public static GetPopularBucketResponse of(List<Bucket> popularBucketList,List<Bucket> recommendBucketList){
        List<BucketElement> popularElements = popularBucketList.stream().limit(4).map(BucketElement::new).toList();
        List<BucketElement> recommendElements = recommendBucketList.stream().limit(4).map(BucketElement::new).toList();

        List<String> popularKeywords = extractKeywordNames(popularElements);
        List<String> recommendKeywords = extractKeywordNames(recommendElements);

        return new GetPopularBucketResponse(popularKeywords, popularElements, recommendKeywords, recommendElements);
    }

    private static List<String> extractKeywordNames(List<BucketElement> elements) {
        if (elements == null || elements.isEmpty()) {
            return List.of();
        }

        Set<String> keywordNames = elements.stream()
            .flatMap(element -> element.keyword().stream())
            .map(KeywordElement::name)
            .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);

        return keywordNames.stream().limit(4).toList();
    }
}
