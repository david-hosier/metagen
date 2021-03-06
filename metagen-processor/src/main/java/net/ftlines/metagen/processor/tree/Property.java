/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package net.ftlines.metagen.processor.tree;

import java.util.Collection;
import java.util.HashSet;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import net.ftlines.metagen.processor.model.Visibility;

public class Property implements Node
{
	private final String name;
	
	public static final Collection<Class<?>> SUPPORTED_ANNOTATIONS = new HashSet<Class<?>>() {{add(Deprecated.class);}};

	/**
	 * name of the field that will hold the property definition in the meta class
	 */
	private String handle;
	private Visibility visibility;
	private Element field;
	private Element getter;
	private Element setter;
	private Collection<Class<?>> additionalAnnotations = new HashSet<Class<?>>();

	public Property(String name)
	{
		this.name = name;
		this.handle = name;
	}

	public Element getField()
	{
		return field;
	}

	public void setField(Element field)
	{
		this.field = field;
	}

	public Element getGetter()
	{
		return getter;
	}

	public void setGetter(Element getter)
	{
		this.getter = getter;
	}

	public Element getSetter()
	{
		return setter;
	}

	public void setSetter(Element setter)
	{
		this.setter = setter;
	}

	public String getName()
	{
		return name;
	}

	public TypeElement getContainer()
	{
		return (TypeElement)getAccessor().getEnclosingElement();
	}

	public TypeMirror getType()
	{
		return getAccessor().asType();
	}

	@Override
	public void accept(Visitor visitor)
	{
		visitor.enterProperty(this);
		visitor.exitProperty(this);
	}

	public Element getAccessor()
	{
		return (getter != null) ? getter : field;
	}

	public Visibility getVisibility()
	{
		if (visibility != null)
		{
			return visibility;
		}
		return Visibility.of(getAccessor());
	}

	public void setVisibility(Visibility visibility)
	{
		this.visibility = visibility;
	}

	public String getHandle()
	{
		return handle;
	}

	public void setHandle(String handle)
	{
		this.handle = handle;
	}

	public void addAnnotation(Class<?> annotation)
	{
		additionalAnnotations.add(annotation);
	}
	
	public Collection<Class<?>> getAdditionalAnnotations()
	{
		return additionalAnnotations;
	}
}
