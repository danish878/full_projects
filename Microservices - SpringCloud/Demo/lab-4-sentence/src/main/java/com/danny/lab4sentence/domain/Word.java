package com.danny.lab4sentence.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 'Word' object is nicely represented in JSON over a regular String.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Word {

	private String word;

	public String getString() {
		return getWord();
	}
}
