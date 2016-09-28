package com.sas.sling.resource;

/*
 * Copyright 2016 Jason E Bailey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.sling.api.resource.Resource;

/**
 * Base class from which the fluent api is constructed to locate resources which
 * we are interested in.
 * 
 * @author JE Bailey
 *
 */
public class ResourceLocator {

	// starting resource
	private Resource resource;

	private Optional<Consumer<Resource>> callback = Optional.empty();

	private Optional<Predicate<Resource>> traversalControl = Optional.empty();

	private long limit = Long.MAX_VALUE;

	private long startOfRange;

	/**
	 * Starting point to locate resources. resources of the start resource.
	 * 
	 * @param resource
	 *            starting point for the traversal
	 * @return new instance of ResourceQuery;
	 */
	public static ResourceLocator startFrom(Resource resource) {
		return new ResourceLocator(resource);
	}

	/*
	 * Constructor to establish the starting resource
	 * 
	 * @param resource
	 */
	private ResourceLocator(Resource resource) {
		this.resource = resource;
	}

	/**
	 * When a matching resource is located, pass that resource to the callback
	 * handler. This is used when the handling of the resources needs to be done
	 * as those resources are identified. This replaces the default
	 * <code>Consumer</code> that appends the resource to the internal list
	 * returned will be returned.
	 * 
	 * @param callback
	 *            Consumer that processes the located resource
	 * @return Resource locator instance
	 */
	public ResourceLocator usingCallback(Consumer<Resource> callback) {
		this.callback = Optional.ofNullable(callback);
		return this;
	}

	/**
	 * When iterating over the child resources, this is used as a validation
	 * that a specific child resource should be traversed
	 * 
	 * This can be used to limit the possible branching options beneath a
	 * resource tree
	 * 
	 * @param condition
	 *            Add child resource if 'true'
	 * @return this locator
	 */
	public ResourceLocator traversalControl(Predicate<Resource> condition) {
		this.traversalControl = Optional.ofNullable(condition);
		return this;
	}

	/**
	 * Sets the maximum number of items to be returned or processed. Starting
	 * from the first matching resource. This method is mutually exclusive to
	 * the range method
	 * 
	 * @param number
	 *            maximum number of items to be returned
	 * @return this locator
	 */
	public ResourceLocator limit(long number) {
		if (number < 0) {
			throw new IllegalArgumentException("value may not be negative");
		}
		this.startOfRange = 0;
		this.limit = number;
		return this;
	}

	/**
	 * Sets the maximum number of items to be returned or processed. Starting
	 * from the nth identified resource as set by the startOfRange. This method
	 * is mutually exclusive to the limit method
	 * 
	 * @param startOfRange
	 * @param limit
	 * @return
	 */
	public ResourceLocator range(long startOfRange, long limit) {
		if (startOfRange < 0 || limit < 0) {
			throw new IllegalArgumentException("value may not be negative");
		}
		this.startOfRange = startOfRange;
		this.limit = limit;
		return this;
	}

	/**
	 * Recursively descends through the available resources and locates
	 * resources that match the provided predicate. Additional restrictions can
	 * be set to limit the paths that the traversal takes, and how the located
	 * resources are handled.
	 * 
	 * @param condition
	 *            predicate to be used against all matching child resources
	 * @return List of matching resource or empty list if callback is enabled
	 */
	public List<Resource> locateResources(Predicate<Resource> condition) {
		List<Resource> resourcesToReturn = new LinkedList<>();
		Deque<Resource> resourcesToCheck = new ArrayDeque<>();

		resourcesToCheck.add(resource);

		long count = 0;
		long max = startOfRange + limit;

		if (max < 0) {
			max = Long.MAX_VALUE;
		}

		while (!resourcesToCheck.isEmpty()) {
			Resource current = resourcesToCheck.pop();
			if (condition.test(current)) {
				++count;
				if (count > startOfRange) {
					callback.orElse(e -> resourcesToReturn.add(e)).accept(current);
				}
			}
			current.listChildren().forEachRemaining(child -> {
				if (traversalControl.orElse(e -> true).test(child)) {
					resourcesToCheck.push(child);
				}
			});
			if (count > max) {
				break;
			}
		}
		return resourcesToReturn;
	}

	/**
	 * Provides a stream of resources starting from the initiator resource and traversing through it's descendants
	 * The only fluent api check it performs is of the traversal predicate.
	 * 
	 * @return self closing {@code Stream<Resource>} of unknown size. 
	 */
	public Stream<Resource> stream(){
		 return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<Resource>() {
			
			Deque<Resource> resourcesToCheck = new ArrayDeque<>();
			
			{
				resourcesToCheck.addFirst(resource);
			}
			
			@Override
			public boolean hasNext() {
				return !resourcesToCheck.isEmpty();
			}

			@Override
			public Resource next() {
				Resource current = resourcesToCheck.removeFirst();
				current.listChildren().forEachRemaining(child -> {
					if (traversalControl.orElse(e -> true).test(child)) {
						resourcesToCheck.addFirst(child);
					}
				});
				return current;
			}
		}, Spliterator.ORDERED | Spliterator.IMMUTABLE),false);
	}

}
