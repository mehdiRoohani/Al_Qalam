package matcher;

import suggest.SuggestMatcher;
public class ContainsMatcher implements SuggestMatcher {
	@Override
	public boolean matches(String dataWord, String searchWord) {
		return dataWord.contains(searchWord);
	}
}