# Example Activities

This is just an example of how the refactoring can be tackled.
The purpose is always to get better code that is not only easier to
read and understand, but also easy to test and maintain.

## Write tests. Only tests. No code changes
There are no tests in the original code, so before refactoring it's always
a good idea to write tests. Why? A couple of reasons:
1. Writing tests helps us understand what the code does in the first place.
Maybe someone else wrote it. Or maybe we wrote it, but completely forgot it.
1. Writing tests helps us refactoring with the confidence that we don't break
any of the original intentions of the code.

We can write some tests for the `Die` class, but all we can assert is whether
the value changes with a roll with respect to the default init value.
This class uses a random integer generator, so it's non-deterministic, and for
testing we need deterministic behaviour. We can't test a random generator.

The `Game` class is even harder to test. All its methods are `void`, so they
don't return anything, so we can't test them. The only interaction with the
rest of the world happens through `System.in` and `System.out`, so how do we
actually test that?

As it turns out, we can. We need to "cheat the `System`", but just a little. 
However, as we will see later, this is only a temporary testing technique that 
we use solely for the purposes of capturing and testing the current behaviour.

